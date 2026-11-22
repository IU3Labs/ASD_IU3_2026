package ru.bmstu.lab02;

import ru.bmstu.lab02.HashMapCustom.Probing;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Бенчмарки для всех задач. Запуск: {@code mvn test-compile exec:java -Dexec.classpathScope=test}
 * Каждое измерение: WARMUP прогонов без учёта + RUNS прогонов, берётся среднее.
 * Стандартные коллекции используются здесь только как эталон для сравнения.
 */
public class Benchmarks {

    static final int WARMUP = 3;
    static final int RUNS = 7;
    static final int[] SIZES = {10_000, 50_000, 100_000};

    static final StringBuilder out = new StringBuilder();
    static volatile long sink; // защита от выбрасывания кода JIT-компилятором

    private static final String[] SECTIONS = {"main", "1B", "1D", "2B", "2C", "2D", "3A", "3B", "3C", "3D"};

    /**
     * Без аргументов: каждый раздел запускается в отдельной JVM (чтобы профиль JIT-компилятора для
     * {@code hashCode/equals} не «загрязнялся» ключами других разделов) и результат собирается в файл.
     * {@code --section X}: выполнить один раздел и вывести его markdown в stdout.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Locale.setDefault(Locale.US);
        if (args.length >= 2 && args[0].equals("--section")) {
            runSection(args[1]);
            System.out.write(out.toString().getBytes(StandardCharsets.UTF_8), 0,
                    out.toString().getBytes(StandardCharsets.UTF_8).length);
            System.out.flush();
            return;
        }
        Path file = Path.of(args.length > 0 ? args[0] : "benchmarks.md");
        StringBuilder all = new StringBuilder();
        all.append("# Бенчмарки\n\n");
        all.append("Среднее время по " + RUNS + " прогонам после " + WARMUP + " прогревочных; Java "
                + System.getProperty("java.version") + ", " + Runtime.getRuntime().availableProcessors()
                + " CPU. Время указано в миллисекундах, если не оговорено иное. "
                + "Данные — случайные `Integer` (seed фиксирован). Каждый раздел выполняется в отдельной JVM. "
                + "Результаты зависят от машины; важны соотношения, а не абсолютные значения.\n\n");
        String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
        for (String section : SECTIONS) {
            ProcessBuilder pb = new ProcessBuilder(java, "-Xms1g", "-Xmx2g", "-cp",
                    System.getProperty("java.class.path"), "ru.bmstu.lab02.Benchmarks", "--section", section);
            pb.redirectError(ProcessBuilder.Redirect.DISCARD);
            Process proc = pb.start();
            all.append(new String(proc.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
            if (proc.waitFor() != 0) {
                throw new IllegalStateException("section " + section + " failed");
            }
            System.out.println("done: " + section);
        }
        all.append("## Общие выводы\n\n{{ВЫВОД_ОБЩИЙ}}\n");
        Files.writeString(file, all.toString(), StandardCharsets.UTF_8);
        System.out.println("Written " + file.toAbsolutePath());
    }

    static void runSection(String name) {
        switch (name) {
            case "main" -> mainTable();
            case "1B" -> task1B();
            case "1D" -> task1D();
            case "2B" -> task2B();
            case "2C" -> task2C();
            case "2D" -> task2D();
            case "3A" -> task3A();
            case "3B" -> task3B();
            case "3C" -> task3C();
            case "3D" -> task3D();
            default -> throw new IllegalArgumentException(name);
        }
    }

    // ------------------------------------------------------------------ главная таблица

    static void mainTable() {
        line("## Сводная таблица: свои структуры против стандартной библиотеки");
        line();
        line("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        line("|---|---|---:|---:|---|");
        for (int n : SIZES) {
            Integer[] data = randomInts(n, 1, Integer.MAX_VALUE);
            double mine = time(() -> {
                PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
                for (Integer x : data) pq.add(x);
                long s = 0;
                while (!pq.isEmpty()) s += pq.poll();
                sink = s;
            });
            double std = time(() -> {
                PriorityQueue<Integer> pq = new PriorityQueue<>();
                for (Integer x : data) pq.add(x);
                long s = 0;
                while (!pq.isEmpty()) s += pq.poll();
                sink = s;
            });
            row("PriorityQueueCustom", "add + poll (цикл)", n, mine, "ratio к стандартной: " + f(mine / std) + "×");
            row("java.util.PriorityQueue", "add + poll (цикл)", n, std, "эталон");
        }
        for (int n : SIZES) {
            Integer[] data = randomInts(n, 2, Integer.MAX_VALUE);
            double std = time(() -> {
                Map<Integer, Integer> m = new HashMap<>();
                for (Integer x : data) m.put(x, x);
                long s = 0;
                for (Integer x : data) s += m.get(x);
                sink = s;
            });
            for (Probing p : Probing.values()) {
                double mine = time(() -> {
                    HashMapCustom<Integer, Integer> m = new HashMapCustom<>(p);
                    for (Integer x : data) m.put(x, x);
                    long s = 0;
                    for (Integer x : data) s += m.get(x);
                    sink = s;
                });
                row("HashMapCustom (" + p.name().toLowerCase() + ")", "put + get (цикл)", n, mine,
                        "ratio к стандартной: " + f(mine / std) + "×");
            }
            row("java.util.HashMap", "put + get (цикл)", n, std, "эталон");
        }
        for (int n : SIZES) {
            Integer[] data = randomInts(n, 3, Integer.MAX_VALUE);
            double mine = time(() -> {
                HashMapCustom<Integer, Integer> m = new HashMapCustom<>();
                for (Integer x : data) m.put(x, x);
                for (Integer x : data) m.remove(x);
                sink = m.size();
            });
            double std = time(() -> {
                Map<Integer, Integer> m = new HashMap<>();
                for (Integer x : data) m.put(x, x);
                for (Integer x : data) m.remove(x);
                sink = m.size();
            });
            row("HashMapCustom (linear)", "put + remove (цикл)", n, mine, "ratio: " + f(mine / std) + "×");
            row("java.util.HashMap", "put + remove (цикл)", n, std, "эталон");
        }
        line();
        line("{{ВЫВОД_ТАБЛИЦА}}");
        line();
    }

    // ------------------------------------------------------------------ 1B

    static void task1B() {
        line("## Задача 1B: min-heap против max-heap (100 000 случайных элементов)");
        line();
        line("Один и тот же набор данных; в каждом прогоне: 100 000 `add`, затем 100 000 `poll`.");
        line();
        line("| Вариант | add (мс) | poll (мс) | add + poll (мс) |");
        line("|---|---:|---:|---:|");
        Integer[] data = randomInts(100_000, 4, Integer.MAX_VALUE);
        heapVariant("min-heap, естественный порядок", data, null);
        heapVariant("min-heap, `Comparator.naturalOrder()`", data, Comparator.naturalOrder());
        heapVariant("max-heap, `Comparator.reverseOrder()`", data, Comparator.reverseOrder());
        line();
        line("{{ВЫВОД_1B}}");
        line();
    }

    static void heapVariant(String name, Integer[] data, Comparator<Integer> cmp) {
        double add = time(() -> {
            PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(cmp);
            for (Integer x : data) pq.add(x);
            sink = pq.size();
        });
        double total = time(() -> {
            PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(cmp);
            for (Integer x : data) pq.add(x);
            long s = 0;
            while (!pq.isEmpty()) s += pq.poll();
            sink = s;
        });
        line("| " + name + " | " + f(add) + " | " + f(total - add) + " | " + f(total) + " |");
    }

    // ------------------------------------------------------------------ 1D

    static void task1D() {
        line("## Задача 1D: построение кучи — последовательные `add` против O(n)");
        line();
        line("| N элементов | Последовательные add (мс) | Конструктор из массива, O(n) (мс) | Ускорение |");
        line("|---:|---:|---:|---:|");
        for (int n : SIZES) {
            Integer[] data = randomInts(n, 5, Integer.MAX_VALUE);
            double seq = time(() -> {
                PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
                for (Integer x : data) pq.add(x);
                sink = pq.size();
            });
            double heapify = time(() -> {
                PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(data);
                sink = pq.size();
            });
            line("| " + n + " | " + f(seq) + " | " + f(heapify) + " | " + f(seq / heapify) + "× |");
        }
        line();
        line("{{ВЫВОД_1D}}");
        line();
    }

    // ------------------------------------------------------------------ 2B

    static void task2B() {
        line("## Задача 2B: среднее число проб на операцию `get` (50 000 операций)");
        line();
        line("Ключи — случайные `Integer`. Число проб — число просмотренных ячеек (включая последнюю). "
                + "Для квадратичного пробирования c1 = 1; c2 = 2 (по умолчанию) и c2 = 3 (пример из задания).");
        line();
        line("| Сценарий | Пробирование | Ёмкость | Загрузка | Проб/get (найден) | Проб/get (не найден) |");
        line("|---|---|---:|---:|---:|---:|");
        // сценарий A: 50 000 вставок при пороге 0.75 (после последнего роста загрузка ≈ 0.38)
        // сценарий B: таблица заполнена до ≈ 0.73 (48 000 ключей в 65 536 ячейках)
        // сценарий C: порог 0.9, 58 000 ключей в 65 536 ячейках (≈ 0.88)
        probeScenario("A: 50 000 ключей, порог 0.75", 50_000, 0.75, 16);
        probeScenario("B: 48 000 ключей в 65 536 ячеек", 48_000, 0.75, 65_536);
        probeScenario("C: 58 000 ключей в 65 536 ячеек, порог 0.9", 58_000, 0.9, 65_536);
        line();
        line("{{ВЫВОД_2B}}");
        line();
    }

    static void probeScenario(String name, int keys, double lf, int startCap) {
        Integer[] data = randomInts(keys, 6, Integer.MAX_VALUE);
        Integer[] missing = randomInts(50_000, 7, Integer.MAX_VALUE);
        int[][] coefs = {{0, 0}, {1, 2}, {1, 3}};
        for (int[] c : coefs) {
            Probing p = c[0] == 0 ? Probing.LINEAR : Probing.QUADRATIC;
            HashMapCustom<Integer, Integer> m = c[0] == 0
                    ? new HashMapCustom<>(p, lf, startCap)
                    : new HashMapCustom<>(p, lf, startCap, c[0], c[1]);
            for (Integer x : data) m.put(x, x);
            m.resetProbeCount();
            for (int i = 0; i < 50_000; i++) sink += m.get(data[i % data.length]);
            double hit = m.probeCount() / 50_000.0;
            m.resetProbeCount();
            for (Integer x : missing) if (m.get(x) != null) sink++;
            double miss = m.probeCount() / 50_000.0;
            String label = c[0] == 0 ? "linear" : "quadratic (c1=1, c2=" + c[1] + ")";
            line("| " + name + " | " + label + " | " + m.capacity() + " | " + f(m.currentLoad()) + " | "
                    + f(hit) + " | " + f(miss) + " |");
        }
    }

    // ------------------------------------------------------------------ 2C

    static void task2C() {
        line("## Задача 2C: искусственные коллизии (`CollisionKey`, hashCode = const)");
        line();
        line("Время (мс) вставки, поиска всех ключей и удаления всех ключей; в последнем столбце — "
                + "среднее число проб на одну операцию `get`.");
        line();
        line("| Пробирование | Ключи | N | put (мс) | get (мс) | remove (мс) | Проб/get |");
        line("|---|---|---:|---:|---:|---:|---:|");
        for (Probing p : Probing.values()) {
            for (int n : new int[]{1_000, 2_500, 5_000, 10_000}) {
                collisionRow(p, n, true);
            }
            collisionRow(p, 10_000, false);
        }
        line();
        line("{{ВЫВОД_2C}}");
        line();
    }

    static void collisionRow(Probing p, int n, boolean collide) {
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) strs[i] = "key" + i;
        double[] put = new double[RUNS], get = new double[RUNS], rem = new double[RUNS];
        double probes = 0;
        for (int r = -WARMUP; r < RUNS; r++) {
            HashMapCustom<Object, Integer> m = new HashMapCustom<>(p);
            Object[] keys = new Object[n];
            for (int i = 0; i < n; i++) keys[i] = collide ? new CollisionKey(strs[i]) : strs[i];
            long t0 = System.nanoTime();
            for (int i = 0; i < n; i++) m.put(keys[i], i);
            long t1 = System.nanoTime();
            m.resetProbeCount();
            long s = 0;
            for (int i = 0; i < n; i++) s += m.get(keys[i]);
            long t2 = System.nanoTime();
            double pr = m.probeCount() / (double) n;
            for (int i = 0; i < n; i++) m.remove(keys[i]);
            long t3 = System.nanoTime();
            sink = s + m.size();
            if (r >= 0) {
                put[r] = (t1 - t0) / 1e6;
                get[r] = (t2 - t1) / 1e6;
                rem[r] = (t3 - t2) / 1e6;
                probes = pr;
            }
        }
        line("| " + p.name().toLowerCase() + " | " + (collide ? "CollisionKey (const hash)" : "String (обычный hash)")
                + " | " + n + " | " + f(avg(put)) + " | " + f(avg(get)) + " | " + f(avg(rem)) + " | " + f(probes) + " |");
    }

    // ------------------------------------------------------------------ 2D

    static void task2D() {
        line("## Задача 2D: пороги загрузки 0.5 / 0.75 / 0.9");
        line();
        line("Линейное пробирование, случайные `Integer`; вставок N, поисков N/2 (найденных). "
                + "Основной эксперимент задания — N = 100 000 / 50 000; дополнительно N = 150 000 / 75 000, "
                + "потому что ёмкость — степень двойки, и при N = 100 000 пороги 0.5 и 0.75 приводят к одной и той же ёмкости. "
                + "Память — размер двух внутренних массивов (ключи и значения; по 4 байта на ссылку при сжатых указателях), "
                + "без учёта самих объектов. Время — среднее на одну операцию в наносекундах (put включает все ресайзы).");
        line();
        line("| Порог | Вставок | Итоговая ёмкость | Фактическая загрузка | Память таблицы (КБ) | put, нс/оп | get (найден), нс/оп | Проб/get (найден) | Проб/get (не найден) |");
        line("|---:|---:|---:|---:|---:|---:|---:|---:|---:|");
        for (int n : new int[]{100_000, 150_000}) {
            Integer[] data = randomInts(n, 8, Integer.MAX_VALUE);
            Integer[] missing = randomInts(n / 2, 9, Integer.MAX_VALUE);
            int gets = n / 2;
            for (double lf : new double[]{0.5, 0.75, 0.9}) {
                double[] put = new double[RUNS], get = new double[RUNS];
                int cap = 0;
                double load = 0, hitProbes = 0, missProbes = 0;
                for (int r = -WARMUP; r < RUNS; r++) {
                    HashMapCustom<Integer, Integer> m = new HashMapCustom<>(Probing.LINEAR, lf);
                    long t0 = System.nanoTime();
                    for (Integer x : data) m.put(x, x);
                    long t1 = System.nanoTime();
                    m.resetProbeCount();
                    long s = 0;
                    for (int i = 0; i < gets; i++) s += m.get(data[i]);
                    long t2 = System.nanoTime();
                    double hp = m.probeCount() / (double) gets;
                    m.resetProbeCount();
                    for (Integer x : missing) if (m.get(x) != null) s++;
                    double mp = m.probeCount() / (double) gets;
                    sink = s;
                    if (r >= 0) {
                        put[r] = (t1 - t0) / (double) n;
                        get[r] = (t2 - t1) / (double) gets;
                        cap = m.capacity();
                        load = m.currentLoad();
                        hitProbes = hp;
                        missProbes = mp;
                    }
                }
                line("| " + lf + " | " + n + " | " + cap + " | " + f(load) + " | " + (cap * 2L * 4 / 1024) + " | "
                        + f(avg(put)) + " | " + f(avg(get)) + " | " + f(hitProbes) + " | " + f(missProbes) + " |");
            }
        }
        line();
        line("{{ВЫВОД_2D}}");
        line();
    }

    // ------------------------------------------------------------------ 3A

    static void task3A() {
        line("## Задача 3A: Top-K на 1 000 000 элементов");
        line();
        line("| k | topK на PriorityQueueCustom, O(n log k) (мс) | Сортировка копии + срез, O(n log n) (мс) | Результаты совпали |");
        line("|---:|---:|---:|---|");
        Integer[] arr = randomInts(1_000_000, 10, Integer.MAX_VALUE);
        List<Integer> data = new ArrayList<>(List.of(arr));
        for (int k : new int[]{10, 1_000, 100_000}) {
            double mine = time(() -> sink = PriorityQueueCustom.topK(data, k).size());
            double sort = time(() -> {
                List<Integer> copy = new ArrayList<>(data);
                copy.sort(Collections.reverseOrder());
                sink = copy.subList(0, k).size();
            });
            List<Integer> copy = new ArrayList<>(data);
            copy.sort(Collections.reverseOrder());
            boolean same = copy.subList(0, k).equals(PriorityQueueCustom.topK(data, k));
            line("| " + k + " | " + f(mine) + " | " + f(sort) + " | " + (same ? "да" : "НЕТ") + " |");
        }
        line();
        line("{{ВЫВОД_3A}}");
        line();
    }

    // ------------------------------------------------------------------ 3B

    static void task3B() {
        String text = generateText(1_000_000, 11);
        line("## Задача 3B: частота слов в тексте ≈ 1 МБ");
        line();
        line("Текст сгенерирован программно (" + text.length() + " символов, словарь 5 000 слов с распределением "
                + "типа Ципфа, случайные заглавные буквы и знаки препинания).");
        line();
        double mine = time(() -> sink = HashMapCustom.countWordsCustom(text).size());
        double std = time(() -> {
            Map<String, Integer> m = new HashMap<>();
            StringBuilder w = new StringBuilder();
            for (int i = 0; i <= text.length(); i++) {
                char c = i < text.length() ? text.charAt(i) : ' ';
                if (Character.isLetterOrDigit(c)) w.append(Character.toLowerCase(c));
                else if (w.length() > 0) {
                    m.merge(w.toString(), 1, Integer::sum);
                    w.setLength(0);
                }
            }
            sink = m.size();
        });
        HashMapCustom<String, Integer> counts = HashMapCustom.countWordsCustom(text);
        line("| Реализация | Время (мс) | Различных слов |");
        line("|---|---:|---:|");
        line("| HashMapCustom | " + f(mine) + " | " + counts.size() + " |");
        line("| java.util.HashMap (`merge`) | " + f(std) + " | " + counts.size() + " |");
        line();
        line("10 самых частых слов:");
        line();
        line("| # | Слово | Количество |");
        line("|---:|---|---:|");
        int i = 1;
        for (HashMapCustom.WordCount wc : HashMapCustom.topWords(counts, 10)) {
            line("| " + i++ + " | " + wc.word() + " | " + wc.count() + " |");
        }
        line();
    }

    // ------------------------------------------------------------------ 3C

    static void task3C() {
        line("## Задача 3C: distinct против `LinkedHashSet`");
        line();
        line("1 000 000 элементов `Integer`, из них около 100 000 различных. Память — прирост занятой кучи "
                + "после `System.gc()` (грубая оценка, ±1 МБ); сами объекты `Integer` существовали до замера и не учитываются. "
                + "`distinct` держит в памяти итоговый список, а хеш-таблица `seen` живёт лишь во время работы, "
                + "поэтому для честного сравнения отдельно показана память самой таблицы.");
        line();
        Integer[] arr = randomInts(1_000_000, 12, 100_000);
        List<Integer> items = new ArrayList<>(List.of(arr));
        double mine = time(() -> sink = HashMapCustom.distinct(items).size());
        double std = time(() -> sink = new ArrayList<>(new LinkedHashSet<>(items)).size());
        long memResult = heapDelta(() -> HashMapCustom.distinct(items));
        long memTable = heapDelta(() -> {
            HashMapCustom<Integer, Boolean> t = new HashMapCustom<>();
            for (Integer x : items) t.putIfAbsent(x, Boolean.TRUE);
            return t;
        });
        long memStd = heapDelta(() -> new LinkedHashSet<>(items));
        boolean same = HashMapCustom.distinct(items).equals(new ArrayList<>(new LinkedHashSet<>(items)));
        line("| Реализация | Время (мс) | Память (МБ, оценка) |");
        line("|---|---:|---:|");
        line("| `HashMapCustom.distinct`: итоговый `ArrayList` | " + f(mine) + " | " + f(memResult / 1048576.0) + " |");
        line("| `HashMapCustom.distinct`: внутренняя `HashMapCustom` (временная) | — | " + f(memTable / 1048576.0) + " |");
        line("| `new LinkedHashSet<>(items)` (держит всё) | " + f(std) + " | " + f(memStd / 1048576.0) + " |");
        line();
        line("Результаты совпали по составу и порядку: " + (same ? "да" : "НЕТ") + ".");
        line();
        line("{{ВЫВОД_3C}}");
        line();
    }

    // ------------------------------------------------------------------ 3D

    static void task3D() {
        line("## Задача 3D: скользящий минимум (n = 200 000, окно k = 1 000)");
        line();
        int n = 200_000, k = 1_000;
        Random rnd = new Random(13);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt();
        int[] asc = new int[n];
        for (int i = 0; i < n; i++) asc[i] = i;
        double mine = time(() -> sink = PriorityQueueCustom.slidingMin(arr, k)[0]);
        double mineAsc = time(() -> sink = PriorityQueueCustom.slidingMin(asc, k)[0]);
        int[] desc = new int[n];
        for (int i = 0; i < n; i++) desc[i] = n - i;
        double mineDesc = time(() -> sink = PriorityQueueCustom.slidingMin(desc, k)[0]);
        double deque = time(() -> sink = dequeSlidingMin(arr, k)[0]);
        boolean same = java.util.Arrays.equals(PriorityQueueCustom.slidingMin(arr, k), dequeSlidingMin(arr, k));
        line("| Реализация | Данные | Время (мс) |");
        line("|---|---|---:|");
        line("| PriorityQueueCustom, O(n log n) | случайные | " + f(mine) + " |");
        line("| PriorityQueueCustom, O(n log n) | возрастающие | " + f(mineAsc) + " |");
        line("| PriorityQueueCustom, O(n log n) | убывающие | " + f(mineDesc) + " |");
        line("| эталон: `ArrayDeque` (монотонная очередь), O(n) | случайные | " + f(deque) + " |");
        line();
        line("Результат совпал с эталоном: " + (same ? "да" : "НЕТ") + ".");
        line();
        line("{{ВЫВОД_3D}}");
        line();
    }

    static int[] dequeSlidingMin(int[] a, int k) {
        int[] res = new int[a.length - k + 1];
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && a[dq.peekLast()] >= a[i]) dq.pollLast();
            dq.addLast(i);
            if (i >= k - 1) res[i - k + 1] = a[dq.peekFirst()];
        }
        return res;
    }

    // ------------------------------------------------------------------ утилиты

    static Integer[] randomInts(int n, long seed, int bound) {
        Random rnd = new Random(seed);
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(bound);
        return a;
    }

    static String generateText(int approxChars, long seed) {
        Random rnd = new Random(seed);
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String[] vocab = new String[5000];
        for (int i = 0; i < vocab.length; i++) {
            int len = 2 + rnd.nextInt(9);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) sb.append(letters.charAt(rnd.nextInt(letters.length())));
            vocab[i] = sb.toString();
        }
        String[] punct = {" ", " ", " ", ", ", ". ", "! ", "? ", "; ", " - ", ": "};
        StringBuilder sb = new StringBuilder(approxChars + 32);
        while (sb.length() < approxChars) {
            // Ципф: индекс ~ vocab * u^3 даёт сильный перекос к началу словаря
            double u = rnd.nextDouble();
            String w = vocab[(int) (vocab.length * u * u * u)];
            if (rnd.nextInt(10) == 0) w = Character.toUpperCase(w.charAt(0)) + w.substring(1);
            if (rnd.nextInt(50) == 0) w = w.toUpperCase();
            sb.append(w).append(punct[rnd.nextInt(punct.length)]);
        }
        return sb.toString();
    }

    static double time(Runnable r) {
        for (int i = 0; i < WARMUP; i++) r.run();
        double total = 0;
        for (int i = 0; i < RUNS; i++) {
            long t = System.nanoTime();
            r.run();
            total += (System.nanoTime() - t) / 1e6;
        }
        return total / RUNS;
    }

    static double avg(double[] a) {
        double s = 0;
        for (double v : a) s += v;
        return s / a.length;
    }

    static long heapDelta(java.util.function.Supplier<Object> builder) {
        Runtime rt = Runtime.getRuntime();
        long best = Long.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            System.gc();
            long before = rt.totalMemory() - rt.freeMemory();
            Object keep = builder.get();
            System.gc();
            long after = rt.totalMemory() - rt.freeMemory();
            sink = System.identityHashCode(keep);
            best = Math.min(best, Math.max(0, after - before));
        }
        return best;
    }

    static void row(String structure, String op, int n, double ms, String note) {
        line("| " + structure + " | " + op + " | " + n + " | " + f(ms) + " | " + note + " |");
    }

    static String f(double v) {
        return String.format(Locale.US, v >= 100 ? "%.1f" : "%.2f", v);
    }

    static void line() {
        out.append('\n');
    }

    static void line(String s) {
        out.append(s).append('\n');
    }

    /** Обёртка над строкой с константным hashCode — все ключи попадают в одну цепочку (задача 2C). */
    public static final class CollisionKey {
        public static final int CONSTANT_HASH = 42;
        private final String value;

        public CollisionKey(String value) {
            this.value = java.util.Objects.requireNonNull(value);
        }

        public String value() {
            return value;
        }

        @Override
        public int hashCode() {
            return CONSTANT_HASH;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CollisionKey other && value.equals(other.value);
        }

        @Override
        public String toString() {
            return "CollisionKey(" + value + ")";
        }
    }
}
