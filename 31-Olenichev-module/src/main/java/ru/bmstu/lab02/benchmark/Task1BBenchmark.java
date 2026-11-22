package ru.bmstu.lab02.benchmark;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import ru.bmstu.lab02.task1b.PriorityQueueComparator;

/**
 * Бенчмарк для задачи 1B: сравнение производительности min-heap и max-heap
 * на наборах из 10 000, 50 000 и 100 000 элементов, а также сравнение со
 * стандартной {@link java.util.PriorityQueue}.
 */
public final class Task1BBenchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_SIZE = 10_000;
    private static final int RUNS_COUNT = 5;
    private static final long RANDOM_SEED = 42L;
    private static final double NANOS_IN_MILLIS = 1_000_000.0;

    private Task1BBenchmark() {
    }

    /**
     * Запускает прогрев JIT и серию замеров для min-heap и max-heap
     * на всех стандартных размерах входных данных.
     */
    public static void run() {
        Random random = new Random(RANDOM_SEED);
        int[] warmup = generateData(WARMUP_SIZE, random);
        measureCustom(warmup, null);
        measureCustom(warmup, Comparator.reverseOrder());
        measureStd(warmup, Comparator.reverseOrder());

        System.out.println("\n=== ЗАДАЧА 1B: Min-Heap vs Max-Heap (10k, 50k, 100k) ===");
        for (int size : SIZES) {
            runForSize(size, random);
        }
    }

    private static void runForSize(int size, Random random) {
        double minCustomTotal = 0.0;
        double maxCustomTotal = 0.0;
        double maxStdTotal = 0.0;

        for (int run = 0; run < RUNS_COUNT; run++) {
            int[] sharedDataset = generateData(size, random);
            minCustomTotal += measureCustom(sharedDataset, null);
            maxCustomTotal += measureCustom(sharedDataset, Comparator.reverseOrder());
            maxStdTotal += measureStd(sharedDataset, Comparator.reverseOrder());
        }

        System.out.printf(
                "N = %-7d | Min-heap: %7.3f ms | Max-heap: %7.3f ms | Std Max-heap: %7.3f ms%n",
                size,
                minCustomTotal / RUNS_COUNT,
                maxCustomTotal / RUNS_COUNT,
                maxStdTotal / RUNS_COUNT
        );
    }

    private static double measureCustom(int[] data, Comparator<Integer> comparator) {
        long start = System.nanoTime();
        PriorityQueueComparator<Integer> queue = new PriorityQueueComparator<>(comparator);
        for (int value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            queue.poll();
        }
        return (System.nanoTime() - start) / NANOS_IN_MILLIS;
    }

    private static double measureStd(int[] data, Comparator<Integer> comparator) {
        long start = System.nanoTime();
        PriorityQueue<Integer> queue = new PriorityQueue<>(comparator);
        for (int value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            queue.poll();
        }
        return (System.nanoTime() - start) / NANOS_IN_MILLIS;
    }

    private static int[] generateData(int size, Random random) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }
}
