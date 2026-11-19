package ru.bmstu.lab02.bench;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import ru.bmstu.lab02.CollisionKey;
import ru.bmstu.lab02.HashMapCustom;
import ru.bmstu.lab02.HashMapCustom.Probing;
import ru.bmstu.lab02.PriorityQueueCustom;
import ru.bmstu.lab02.TopK;
import ru.bmstu.lab02.WordCount;
import ru.bmstu.lab02.WordCounter;

/**
 * Бенчмарки лабораторной работы 2. Стандартные коллекции используются здесь только для сравнения.
 *
 * <p>Запуск: {@code java -cp target/classes ru.bmstu.lab02.bench.BenchmarkRunner [файл-для-вывода.md]}.
 * Без аргумента таблицы печатаются в консоль.
 */
public final class BenchmarkRunner {

    private static final long SEED = 20260101L;
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_RUNS = 3;
    private static final int MEASURED_RUNS = 7;
    private static final double NANOS_IN_MILLI = 1_000_000.0;
    private static final int COMPARISON_ELEMENTS = 100_000;
    private static final int PROBE_OPERATIONS = 50_000;
    private static final int[] COLLISION_SIZES = {1_000, 2_000, 5_000, 10_000};
    private static final double[] LOAD_FACTORS = {0.5, 0.75, 0.9};
    private static final int RESIZE_INSERTS = 100_000;
    private static final int RESIZE_LOOKUPS = 50_000;
    private static final int TOP_K_INPUT = 1_000_000;
    private static final int TOP_K = 100;
    private static final int TEXT_TARGET_BYTES = 1_000_000;
    private static final int VOCABULARY_SIZE = 5_000;
    private static final int TOP_WORDS = 10;
    private static final int REFERENCE_BYTES = 4;
    private static final int ARRAYS_IN_TABLE = 2;

    /** Результат вычислений складывается сюда, чтобы JIT не выбросил измеряемый код как «мёртвый». */
    private static final AtomicLong SINK = new AtomicLong();

    private final StringBuilder out = new StringBuilder();

    private BenchmarkRunner() {
    }

    /**
     * Точка входа.
     *
     * @param args необязательный путь к файлу, в который записать результат
     * @throws IOException если файл не удалось записать
     */
    public static void main(String[] args) throws IOException {
        BenchmarkRunner runner = new BenchmarkRunner();
        runner.runAll();
        String report = runner.out.toString();
        if (args.length > 0) {
            Files.writeString(Path.of(args[0]), report);
        }
        System.out.print(report);
    }

    private void runAll() {
        header();
        mainComparison();
        minVersusMax();
        buildHeap();
        probeComparison();
        collisionDegradation();
        resizeThresholds();
        topK();
        wordCount();
    }

    // ---------- разделы отчёта ----------

    private void header() {
        line("Среда: Java " + System.getProperty("java.version") + ", " + System.getProperty("os.name")
                + ", процессоров: " + Runtime.getRuntime().availableProcessors() + ". Прогревочных запусков: "
                + WARMUP_RUNS + ", измеряемых: " + MEASURED_RUNS + " (в таблицах среднее). Данные случайные, seed = "
                + SEED + ".");
        line("");
    }

    private void mainComparison() {
        line("## Основная таблица");
        line("");
        line("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        line("|---|---|---|---|---|");
        for (int n : SIZES) {
            List<Integer> data = randomInts(n);
            row("PriorityQueueCustom", "add + poll (цикл)", n, measure(() -> customQueueRun(data)), "min-heap");
            row("java.util.PriorityQueue", "add + poll (цикл)", n, measure(() -> javaQueueRun(data)), "min-heap");
        }
        for (int n : SIZES) {
            List<Integer> data = randomInts(n);
            row("HashMapCustom", "put + get (цикл)", n, measure(() -> customMapRun(Probing.LINEAR, data)),
                    "линейное пробирование, порог 0.75");
            row("HashMapCustom", "put + get (цикл)", n, measure(() -> customMapRun(Probing.QUADRATIC, data)),
                    "квадратичное пробирование, порог 0.75");
            row("java.util.HashMap", "put + get (цикл)", n, measure(() -> javaMapRun(data)), "цепочки/деревья");
        }
        line("");
    }

    private void minVersusMax() {
        line("## 1B. Min-heap и max-heap на одинаковых данных (100 000 элементов)");
        line("");
        List<Integer> data = randomInts(COMPARISON_ELEMENTS);
        double min = measure(() -> queueRun(new PriorityQueueCustom<>(Comparator.naturalOrder()), data));
        double max = measure(() -> queueRun(new PriorityQueueCustom<>(Comparator.reverseOrder()), data));
        long minComparisons = countComparisons(data, false);
        long maxComparisons = countComparisons(data, true);
        line("| Вариант | add + poll, среднее (мс) | Число сравнений |");
        line("|---|---|---|");
        line(String.format(Locale.ROOT, "| min-heap (`naturalOrder`) | %.2f | %d |", min, minComparisons));
        line(String.format(Locale.ROOT, "| max-heap (`reverseOrder`) | %.2f | %d |", max, maxComparisons));
        line("");
    }

    private void buildHeap() {
        line("## 1D. Построение кучи: последовательные add против конструктора из массива (100 000 элементов)");
        line("");
        List<Integer> data = randomInts(COMPARISON_ELEMENTS);
        Integer[] array = data.toArray(new Integer[0]);
        double byAdd = measure(() -> SINK.addAndGet(addAll(data).size()));
        double byHeapify = measure(() -> SINK.addAndGet(new PriorityQueueCustom<>(array).size()));
        line("| Способ | Асимптотика | Среднее время (мс) |");
        line("|---|---|---|");
        line(String.format(Locale.ROOT, "| 100 000 раз `add` | O(n log n) | %.2f |", byAdd));
        line(String.format(Locale.ROOT, "| `new PriorityQueueCustom<>(T[])` | O(n) | %.2f |", byHeapify));
        line("");
    }

    private void probeComparison() {
        line("## 2B. Среднее число проб на `get` (50 000 ключей, 50 000 успешных и 50 000 неуспешных `get`)");
        line("");
        line("| Пробирование | Порог загрузки | Ёмкость | Проб на успешный get | Проб на неуспешный get |");
        line("|---|---|---|---|---|");
        List<Integer> keys = randomInts(PROBE_OPERATIONS);
        List<Integer> absent = absentKeys(keys, PROBE_OPERATIONS);
        for (double loadFactor : new double[] {0.75, 0.9}) {
            for (Probing probing : Probing.values()) {
                HashMapCustom<Integer, Integer> map = new HashMapCustom<>(probing, 16, loadFactor);
                keys.forEach(key -> map.put(key, key));
                double hits = averageProbes(map, keys);
                double misses = averageProbes(map, absent);
                line(String.format(Locale.ROOT, "| %s | %.2f | %d | %.3f | %.3f |", probing, loadFactor,
                        map.capacity(), hits, misses));
            }
        }
        line("");
    }

    private void collisionDegradation() {
        line("## 2C. Деградация при одинаковом `hashCode` (линейное пробирование, put + get + remove для n ключей)");
        line("");
        line("| n | Обычные строковые ключи (мс) | `CollisionKey` (мс) | Замедление | Проб на get (CollisionKey) |");
        line("|---|---|---|---|---|");
        for (int n : COLLISION_SIZES) {
            List<String> names = names(n);
            double normal = measure(() -> collisionRun(names, false));
            double colliding = measure(() -> collisionRun(names, true));
            HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
            names.forEach(name -> map.put(new CollisionKey(name), 1));
            map.resetProbeStatistics();
            names.forEach(name -> map.get(new CollisionKey(name)));
            line(String.format(Locale.ROOT, "| %d | %.2f | %.2f | в %.0f раз | %.0f |", n, normal, colliding,
                    colliding / normal, map.averageLookupProbes()));
        }
        line("");
    }

    private void resizeThresholds() {
        line("## 2D. Порог загрузки (100 000 вставок, 50 000 поисков, линейное пробирование)");
        line("");
        line("| Порог | Итоговая ёмкость | Память под ячейки, оценка (КБ) | put, среднее (мс) | get, среднее (мс) "
                + "| Проб на get |");
        line("|---|---|---|---|---|---|");
        List<Integer> keys = randomInts(RESIZE_INSERTS);
        List<Integer> lookups = keys.subList(0, RESIZE_LOOKUPS);
        for (double loadFactor : LOAD_FACTORS) {
            double put = measure(() -> SINK.addAndGet(fill(loadFactor, keys).size()));
            HashMapCustom<Integer, Integer> map = fill(loadFactor, keys);
            double get = measure(() -> getAll(map, lookups));
            map.resetProbeStatistics();
            getAll(map, lookups);
            long kilobytes = (long) map.capacity() * ARRAYS_IN_TABLE * REFERENCE_BYTES / 1024;
            line(String.format(Locale.ROOT, "| %.2f | %d | %d | %.2f | %.2f | %.2f |", loadFactor, map.capacity(),
                    kilobytes, put, get, map.averageLookupProbes()));
        }
        line("");
        line("Память: два массива ссылок (`keys`, `values`) по 4 байта на ссылку (сжатые указатели HotSpot); "
                + "сами объекты `Integer` у всех вариантов одинаковы и не учитываются.");
        line("");
    }

    private void topK() {
        line("## 3A. Top-K (1 000 000 элементов, k = 100)");
        line("");
        List<Integer> data = randomInts(TOP_K_INPUT);
        double heap = measure(() -> SINK.addAndGet(TopK.topK(data, TOP_K).size()));
        double sort = measure(() -> SINK.addAndGet(topBySorting(data, TOP_K).size()));
        line("| Способ | Асимптотика | Среднее время (мс) |");
        line("|---|---|---|");
        line(String.format(Locale.ROOT, "| `TopK.topK` (своя куча) | O(n log k) | %.2f |", heap));
        line(String.format(Locale.ROOT, "| Полная сортировка копии и срез | O(n log n) | %.2f |", sort));
        line("");
    }

    private void wordCount() {
        String text = generateText();
        double time = measure(() -> SINK.addAndGet(WordCounter.countWords(text).size()));
        line("## 3B. Частота слов (текст ≈ 1 МБ)");
        line("");
        line(String.format(Locale.ROOT, "Размер текста: %d символов, подсчёт `countWords`: %.2f мс (среднее).",
                text.length(), time));
        line("");
        line("10 самых частых слов:");
        line("");
        line("| № | Слово | Вхождений |");
        line("|---|---|---|");
        List<WordCount> top = WordCounter.topWords(text, TOP_WORDS);
        for (int i = 0; i < top.size(); i++) {
            line(String.format(Locale.ROOT, "| %d | %s | %d |", i + 1, top.get(i).word(), top.get(i).count()));
        }
        line("");
    }

    // ---------- измеряемые операции ----------

    private static void customQueueRun(List<Integer> data) {
        queueRun(new PriorityQueueCustom<>(), data);
    }

    private static void queueRun(PriorityQueueCustom<Integer> queue, List<Integer> data) {
        data.forEach(queue::add);
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        SINK.addAndGet(sum);
    }

    private static void javaQueueRun(List<Integer> data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        data.forEach(queue::add);
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        SINK.addAndGet(sum);
    }

    private static PriorityQueueCustom<Integer> addAll(List<Integer> data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        data.forEach(queue::add);
        return queue;
    }

    private static void customMapRun(Probing probing, List<Integer> data) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(probing);
        data.forEach(key -> map.put(key, key));
        getAll(map, data);
    }

    private static void javaMapRun(List<Integer> data) {
        Map<Integer, Integer> map = new HashMap<>();
        data.forEach(key -> map.put(key, key));
        long sum = 0;
        for (Integer key : data) {
            sum += map.get(key);
        }
        SINK.addAndGet(sum);
    }

    private static void getAll(HashMapCustom<Integer, Integer> map, List<Integer> keys) {
        long sum = 0;
        for (Integer key : keys) {
            Integer value = map.get(key);
            sum += value == null ? 0 : value;
        }
        SINK.addAndGet(sum);
    }

    private static HashMapCustom<Integer, Integer> fill(double loadFactor, List<Integer> keys) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(Probing.LINEAR, 16, loadFactor);
        keys.forEach(key -> map.put(key, key));
        return map;
    }

    private static void collisionRun(List<String> names, boolean colliding) {
        if (colliding) {
            HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
            names.forEach(name -> map.put(new CollisionKey(name), 1));
            names.forEach(name -> SINK.addAndGet(map.get(new CollisionKey(name))));
            names.forEach(name -> map.remove(new CollisionKey(name)));
        } else {
            HashMapCustom<String, Integer> map = new HashMapCustom<>();
            names.forEach(name -> map.put(name, 1));
            names.forEach(name -> SINK.addAndGet(map.get(name)));
            names.forEach(map::remove);
        }
    }

    private static List<Integer> topBySorting(List<Integer> data, int k) {
        List<Integer> copy = new ArrayList<>(data);
        copy.sort(Collections.reverseOrder());
        return copy.subList(0, k);
    }

    // ---------- вспомогательные методы ----------

    private static double averageProbes(HashMapCustom<Integer, Integer> map, List<Integer> keys) {
        map.resetProbeStatistics();
        getAll(map, keys);
        return map.averageLookupProbes();
    }

    private static long countComparisons(List<Integer> data, boolean reversed) {
        long[] counter = new long[1];
        Comparator<Integer> base = reversed ? Comparator.reverseOrder() : Comparator.naturalOrder();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>((a, b) -> {
            counter[0]++;
            return base.compare(a, b);
        });
        queueRun(queue, data);
        return counter[0];
    }

    private static double measure(Runnable action) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            action.run();
        }
        long total = 0;
        for (int i = 0; i < MEASURED_RUNS; i++) {
            long start = System.nanoTime();
            action.run();
            total += System.nanoTime() - start;
        }
        return total / (double) MEASURED_RUNS / NANOS_IN_MILLI;
    }

    private static List<Integer> randomInts(int count) {
        Random random = new Random(SEED);
        List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(random.nextInt());
        }
        return result;
    }

    /** Случайные ключи, которых заведомо нет среди {@code present} (для неуспешных поисков). */
    private static List<Integer> absentKeys(List<Integer> present, int count) {
        HashMap<Integer, Boolean> known = new HashMap<>();
        present.forEach(key -> known.put(key, true));
        Random random = new Random(SEED + 1);
        List<Integer> result = new ArrayList<>(count);
        while (result.size() < count) {
            int candidate = random.nextInt();
            if (!known.containsKey(candidate)) {
                result.add(candidate);
            }
        }
        return result;
    }

    private static List<String> names(int count) {
        List<String> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add("key-" + i);
        }
        return result;
    }

    /** Текст из словаря с распределением, похожим на естественный язык: слово с номером r берётся ~ 1/r. */
    private static String generateText() {
        Random random = new Random(SEED);
        String[] vocabulary = new String[VOCABULARY_SIZE];
        for (int i = 0; i < VOCABULARY_SIZE; i++) {
            vocabulary[i] = "слово" + Integer.toString(i, Character.MAX_RADIX);
        }
        StringBuilder text = new StringBuilder();
        while (text.length() < TEXT_TARGET_BYTES) {
            int rank = (int) Math.pow(VOCABULARY_SIZE, random.nextDouble()) - 1;
            String word = vocabulary[rank];
            text.append(random.nextInt(10) == 0 ? Character.toUpperCase(word.charAt(0)) + word.substring(1) : word);
            text.append(random.nextInt(8) == 0 ? ". " : " ");
        }
        return text.toString();
    }

    private void row(String structure, String operation, int n, double millis, String note) {
        line(String.format(Locale.ROOT, "| `%s` | %s | %d | %.2f | %s |", structure, operation, n, millis, note));
    }

    private void line(String text) {
        out.append(text).append('\n');
    }
}
