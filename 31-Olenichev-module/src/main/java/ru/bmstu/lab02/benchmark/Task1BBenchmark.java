package ru.bmstu.lab02.benchmark;

import java.util.Comparator;
import java.util.Random;
import ru.bmstu.lab02.task1b.PriorityQueueComparator;

/**
 * Бенчмарк для задачи 1B: сравнение производительности min-heap и max-heap
 * на одном и том же наборе из 100 000 случайных элементов.
 */
public final class Task1BBenchmark {

    private static final int DATASET_SIZE = 100_000;
    private static final int WARMUP_SIZE = 10_000;
    private static final int RUNS_COUNT = 5;
    private static final long RANDOM_SEED = 42L;
    private static final double NANOS_IN_MILLIS = 1_000_000.0;

    private Task1BBenchmark() {
    }

    /**
     * Запускает прогрев JIT и сравнение min-heap против max-heap.
     */
    public static void run() {
        Random random = new Random(RANDOM_SEED);
        int[] warmup = generateData(WARMUP_SIZE, random);
        measureQueue(warmup, null);
        measureQueue(warmup, Comparator.reverseOrder());

        int[] sharedDataset = generateData(DATASET_SIZE, random);
        double minTotal = 0.0;
        double maxTotal = 0.0;

        for (int run = 0; run < RUNS_COUNT; run++) {
            minTotal += measureQueue(sharedDataset, null);
            maxTotal += measureQueue(sharedDataset, Comparator.reverseOrder());
        }

        System.out.println("\n=== ЗАДАЧА 1B: Min-Heap vs Max-Heap (N = 100 000) ===");
        System.out.printf("Min-heap (natural): %7.3f ms | Max-heap (reverseOrder): %7.3f ms%n",
                minTotal / RUNS_COUNT, maxTotal / RUNS_COUNT);
    }

    private static double measureQueue(int[] data, Comparator<Integer> comparator) {
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

    private static int[] generateData(int size, Random random) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }
}
