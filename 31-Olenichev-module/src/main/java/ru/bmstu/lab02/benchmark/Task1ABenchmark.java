package ru.bmstu.lab02.benchmark;

import java.util.PriorityQueue;
import java.util.Random;
import ru.bmstu.lab02.task1a.PriorityQueueCustom;

/**
 * Бенчмарк для задачи 1A: сравнение {@link PriorityQueueCustom}
 * со стандартной реализацией {@link java.util.PriorityQueue}.
 */
public final class Task1ABenchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_SIZE = 10_000;
    private static final int RUNS_COUNT = 5;
    private static final long RANDOM_SEED = 42L;
    private static final double NANOS_IN_MILLIS = 1_000_000.0;

    private Task1ABenchmark() {
    }

    /**
     * Запускает прогрев JIT и серию замеров для задачи 1A.
     */
    public static void run() {
        Random random = new Random(RANDOM_SEED);
        measureCustom(generateData(WARMUP_SIZE, random));
        measureStd(generateData(WARMUP_SIZE, random));

        System.out.println("=== ЗАДАЧА 1A: PriorityQueueCustom vs java.util.PriorityQueue ===");
        for (int size : SIZES) {
            runForSize(size, random);
        }
    }

    private static void runForSize(int size, Random random) {
        double customTotal = 0.0;
        double stdTotal = 0.0;

        for (int run = 0; run < RUNS_COUNT; run++) {
            int[] data = generateData(size, random);
            customTotal += measureCustom(data);
            stdTotal += measureStd(data);
        }

        System.out.printf("N = %-7d | Custom: %7.3f ms | Std: %7.3f ms%n",
                size, customTotal / RUNS_COUNT, stdTotal / RUNS_COUNT);
    }

    private static double measureCustom(int[] data) {
        long start = System.nanoTime();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            queue.poll();
        }
        return (System.nanoTime() - start) / NANOS_IN_MILLIS;
    }

    private static double measureStd(int[] data) {
        long start = System.nanoTime();
        PriorityQueue<Integer> queue = new PriorityQueue<>();
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
