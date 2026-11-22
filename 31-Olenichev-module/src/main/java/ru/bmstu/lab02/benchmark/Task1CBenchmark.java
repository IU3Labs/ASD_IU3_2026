package ru.bmstu.lab02.benchmark;

import java.util.Random;
import ru.bmstu.lab02.task1c.PriorityQueueRemovable;

/**
 * Бенчмарк для задачи 1C: замеры производительности {@link PriorityQueueRemovable}
 * на наборах из 10 000, 50 000 и 100 000 элементов.
 */
public final class Task1CBenchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_SIZE = 10_000;
    private static final int RUNS_COUNT = 5;
    private static final long RANDOM_SEED = 42L;
    private static final double NANOS_IN_MILLIS = 1_000_000.0;

    private Task1CBenchmark() {
    }

    /**
     * Запускает прогрев JIT и серию замеров для задачи 1C.
     */
    public static void run() {
        Random random = new Random(RANDOM_SEED);
        measureCycle(generateData(WARMUP_SIZE, random));

        System.out.println("\n=== ЗАДАЧА 1C: PriorityQueueRemovable (add + poll) ===");
        for (int size : SIZES) {
            runForSize(size, random);
        }
    }

    private static void runForSize(int size, Random random) {
        double totalMs = 0.0;

        for (int run = 0; run < RUNS_COUNT; run++) {
            int[] data = generateData(size, random);
            totalMs += measureCycle(data);
        }

        System.out.printf("N = %-7d | PriorityQueueRemovable (add + poll): %7.3f ms%n",
                size, totalMs / RUNS_COUNT);
    }

    private static double measureCycle(int[] data) {
        long start = System.nanoTime();
        PriorityQueueRemovable<Integer> queue = new PriorityQueueRemovable<>();
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
