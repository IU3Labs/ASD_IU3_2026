/*Бенчмарки ЛР2
Замеры на 10 000, 50 000 и 100 000 элементов: PriorityQueueCustom против java.util.PriorityQueue,
HashMapCustom против java.util.HashMap, min- против max-кучи (1B), последовательные add
против построения за O(n) (1D), topK на 1 000 000 (3A), скользящий минимум 200 000 / окно 1 000 (3D).
Стандартные коллекции используются здесь только для сравнения. */

package ru.bmstu.lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Замеры времени через {@link System#nanoTime()}: несколько прогревочных запусков,
 * затем среднее по нескольким измеряемым. Результат печатается строками markdown-таблицы.
 * Запуск: {@code java -cp target/classes ru.bmstu.lab2.Benchmark}.
 */
public final class Benchmark {
    /** Размеры наборов данных для основных замеров. */
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    /** Количество прогревочных запусков (JIT-компиляция), в среднее не входят. */
    private static final int WARMUP_RUNS = 5;
    /** Количество измеряемых запусков, по которым берётся среднее. */
    private static final int MEASURED_RUNS = 10;
    /** Размер входа для topK (3A). */
    private static final int TOP_K_N = 1_000_000;
    /** Значения k для topK (3A). */
    private static final int[] TOP_K_VALUES = {10, 100, 1_000};
    /** Размер массива для скользящего минимума (3D). */
    private static final int SLIDING_N = 200_000;
    /** Размер окна для скользящего минимума (3D). */
    private static final int SLIDING_WINDOW = 1_000;
    /** Зерно генератора — одинаковые данные при каждом запуске. */
    private static final long SEED = 42;
    /** Наносекунд в миллисекунде. */
    private static final double NANOS_PER_MS = 1_000_000.0;

    /** Сумма результатов всех задач: печатается в конце, чтобы JIT не выбросил вычисления. */
    private static long blackhole;

    /**
     * Замеряемое действие; возвращает контрольное число, зависящее от результата.
     */
    @FunctionalInterface
    private interface Task {
        long run();
    }

    private Benchmark() {
    }

    /**
     * Запускает все замеры и печатает таблицу.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---|---|---|");
        for (int n : SIZES) {
            benchPriorityQueues(n);
        }
        for (int n : SIZES) {
            benchHashMaps(n);
        }
        for (int n : SIZES) {
            benchMinVsMax(n);
        }
        for (int n : SIZES) {
            benchBuild(n);
        }
        benchTopK();
        benchSlidingMinimum();
        System.out.println();
        System.out.println("контрольная сумма: " + blackhole);
    }

    /**
     * add всех элементов, затем poll до опустошения: своя очередь против java.util.PriorityQueue.
     */
    private static void benchPriorityQueues(int n) {
        Integer[] data = randomBoxed(n);
        row("`PriorityQueueCustom`", "add + poll (цикл)", n, measure(() -> {
            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
            for (Integer x : data) {
                queue.add(x);
            }
            long sum = 0;
            while (!queue.isEmpty()) {
                sum += queue.poll();
            }
            return sum;
        }), "min-куча, случайные int");
        row("`java.util.PriorityQueue`", "add + poll (цикл)", n, measure(() -> {
            PriorityQueue<Integer> queue = new PriorityQueue<>();
            for (Integer x : data) {
                queue.add(x);
            }
            long sum = 0;
            while (!queue.isEmpty()) {
                sum += queue.poll();
            }
            return sum;
        }), "min-куча, случайные int");
    }

    /**
     * put n ключей в пустую таблицу и get всех n ключей: своя таблица против java.util.HashMap.
     */
    private static void benchHashMaps(int n) {
        Integer[] keys = randomBoxed(n);
        HashMapCustom<Integer, Integer> filledCustom = new HashMapCustom<>();
        HashMap<Integer, Integer> filledJava = new HashMap<>();
        for (Integer key : keys) {
            filledCustom.put(key, key);
            filledJava.put(key, key);
        }
        row("`HashMapCustom`", "put", n, measure(() -> {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
            for (Integer key : keys) {
                map.put(key, key);
            }
            return map.size();
        }), "с ёмкости 16, ресайзы включены");
        row("`java.util.HashMap`", "put", n, measure(() -> {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (Integer key : keys) {
                map.put(key, key);
            }
            return map.size();
        }), "с ёмкости 16, ресайзы включены");
        row("`HashMapCustom`", "get", n, measure(() -> {
            long sum = 0;
            for (Integer key : keys) {
                sum += filledCustom.get(key);
            }
            return sum;
        }), "все ключи присутствуют");
        row("`java.util.HashMap`", "get", n, measure(() -> {
            long sum = 0;
            for (Integer key : keys) {
                sum += filledJava.get(key);
            }
            return sum;
        }), "все ключи присутствуют");
    }

    /**
     * 1B: одна и та же своя очередь в режимах min и max на одинаковых данных.
     */
    private static void benchMinVsMax(int n) {
        Integer[] data = randomBoxed(n);
        row("`PriorityQueueCustom` min", "add + poll (цикл)", n,
                measure(() -> addPoll(new PriorityQueueCustom<>(Comparator.naturalOrder()), data)),
                "`Comparator.naturalOrder()`");
        row("`PriorityQueueCustom` max", "add + poll (цикл)", n,
                measure(() -> addPoll(new PriorityQueueCustom<>(Comparator.reverseOrder()), data)),
                "`Comparator.reverseOrder()`");
    }

    /**
     * 1D: построение кучи последовательными add против конструктора из массива за O(n).
     */
    private static void benchBuild(int n) {
        Integer[] data = randomBoxed(n);
        row("`PriorityQueueCustom`", "построение: n × add", n, measure(() -> {
            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
            for (Integer x : data) {
                queue.add(x);
            }
            return queue.peek();
        }), "O(n log n) в худшем случае");
        row("`PriorityQueueCustom`", "построение: `new PriorityQueueCustom<>(T[])`", n, measure(() -> {
            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(data);
            return queue.peek();
        }), "O(n), просеивание вниз");
    }

    /**
     * 3A: topK на 1 000 000 элементов против сортировки всего списка.
     */
    private static void benchTopK() {
        List<Integer> input = new ArrayList<>(List.of(randomBoxed(TOP_K_N)));
        for (int k : TOP_K_VALUES) {
            row("`TopK.topK`", "k = " + k, TOP_K_N,
                    measure(() -> TopK.topK(input, k).get(0)), "min-куча размера k, O(n log k)");
        }
        row("сортировка (эталон)", "копия + `sort` + первые k", TOP_K_N, measure(() -> {
            List<Integer> copy = new ArrayList<>(input);
            copy.sort(Collections.reverseOrder());
            return copy.get(0);
        }), "O(n log n), не зависит от k");
    }

    /**
     * 3D: скользящий минимум через кучу против простого перебора каждого окна.
     */
    private static void benchSlidingMinimum() {
        int[] arr = new Random(SEED).ints(SLIDING_N).toArray();
        row("`SlidingMinimum.slidingMin`", "окно " + SLIDING_WINDOW, SLIDING_N,
                measure(() -> SlidingMinimum.slidingMin(arr, SLIDING_WINDOW)[0]), "куча с ленивым удалением");
        row("перебор (эталон)", "окно " + SLIDING_WINDOW, SLIDING_N, measure(() -> {
            long sum = 0;
            for (int left = 0; left + SLIDING_WINDOW <= arr.length; left++) {
                int min = arr[left];
                for (int i = left + 1; i < left + SLIDING_WINDOW; i++) {
                    min = Math.min(min, arr[i]);
                }
                sum += min;
            }
            return sum;
        }), "O(n · k)");
    }

    /**
     * Добавляет все элементы и извлекает их до опустошения.
     */
    private static long addPoll(PriorityQueueCustom<Integer> queue, Integer[] data) {
        for (Integer x : data) {
            queue.add(x);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    /**
     * Прогревает задачу, затем возвращает среднее время измеряемых запусков в миллисекундах.
     */
    private static double measure(Task task) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            blackhole += task.run();
        }
        long total = 0;
        for (int i = 0; i < MEASURED_RUNS; i++) {
            long start = System.nanoTime();
            blackhole += task.run();
            total += System.nanoTime() - start;
        }
        return total / NANOS_PER_MS / MEASURED_RUNS;
    }

    /**
     * Массив из n случайных Integer (упаковка делается заранее и не попадает в замер).
     */
    private static Integer[] randomBoxed(int n) {
        Random random = new Random(SEED + n);
        Integer[] data = new Integer[n];
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    /**
     * Печатает строку таблицы.
     */
    private static void row(String structure, String operation, int n, double ms, String note) {
        // Locale.ROOT: точка в дробной части; разряды N разделяем обычным пробелом, как в задании
        String count = String.format(Locale.ROOT, "%,d", n).replace(',', ' ');
        System.out.printf(Locale.ROOT, "| %s | %s | %s | %.3f | %s |%n", structure, operation, count, ms, note);
    }
}
