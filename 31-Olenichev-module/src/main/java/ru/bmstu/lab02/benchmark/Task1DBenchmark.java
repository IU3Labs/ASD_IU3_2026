package ru.bmstu.lab02.benchmark;

import java.util.Random;
import ru.bmstu.lab02.task1d.PriorityQueueHeapify;

/**
 * Бенчмарк для задачи 1D: сравнение построения кучи последовательными add
 * против O(n) конструктора, а также замеры add+poll на 10k, 50k и 100k элементов.
 */
public final class Task1DBenchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_SIZE = 10_000;
    private static final int RUNS_COUNT = 5;
    private static final long RANDOM_SEED = 42L;
    private static final double NANOS_IN_MILLIS = 1_000_000.0;

    private Task1DBenchmark() {
    }

    /**
     * Запускает прогрев JIT и серию замеров для задачи 1D.
     */
    public static void run() {
        Random random = new Random(RANDOM_SEED);
        Integer[] warmup = generateBoxedArray(WARMUP_SIZE, random);
        measureSequentialAdd(warmup);
        measureBottomUpHeapify(warmup);
        measureCycle(warmup);

        System.out.println("\n=== ЗАДАЧА 1D: Последовательные add vs O(n) Heapify ===");
        for (int size : SIZES) {
            runBuildComparisonForSize(size, random);
        }

        System.out.println("\n=== ЗАДАЧА 1D: PriorityQueueHeapify (add + poll) ===");
        for (int size : SIZES) {
            runCycleForSize(size, random);
        }
    }

    private static void runBuildComparisonForSize(int size, Random random) {
        double seqTotal = 0.0;
        double heapifyTotal = 0.0;

        for (int run = 0; run < RUNS_COUNT; run++) {
            Integer[] data = generateBoxedArray(size, random);
            seqTotal += measureSequentialAdd(data);
            heapifyTotal += measureBottomUpHeapify(data);
        }

        System.out.printf("N = %-7d | Sequential add O(n log n): %7.3f ms | Bottom-up heapify O(n): %7.3f ms%n",
                size, seqTotal / RUNS_COUNT, heapifyTotal / RUNS_COUNT);
    }

    private static void runCycleForSize(int size, Random random) {
        double cycleTotal = 0.0;

        for (int run = 0; run < RUNS_COUNT; run++) {
            Integer[] data = generateBoxedArray(size, random);
            cycleTotal += measureCycle(data);
        }

        System.out.printf("N = %-7d | PriorityQueueHeapify (add + poll): %7.3f ms%n",
                size, cycleTotal / RUNS_COUNT);
    }

    private static double measureSequentialAdd(Integer[] data) {
        long start = System.nanoTime();
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>();
        for (Integer item : data) {
            queue.add(item);
        }
        return (System.nanoTime() - start) / NANOS_IN_MILLIS;
    }

    private static double measureBottomUpHeapify(Integer[] data) {
        long start = System.nanoTime();
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>(data);
        return (System.nanoTime() - start) / NANOS_IN_MILLIS;
    }

    private static double measureCycle(Integer[] data) {
        long start = System.nanoTime();
        PriorityQueueHeapify<Integer> queue = new PriorityQueueHeapify<>();
        for (Integer item : data) {
            queue.add(item);
        }
        while (!queue.isEmpty()) {
            queue.poll();
        }
        return (System.nanoTime() - start) / NANOS_IN_MILLIS;
    }

    private static Integer[] generateBoxedArray(int size, Random random) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
}