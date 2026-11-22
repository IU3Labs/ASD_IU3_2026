package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Supplier;

/** Последовательно измеряет операции на одинаковых входных данных. */
public final class QueueBenchmark {
    private static final int WARMUP = 5;
    private static final int REPEATS = 12;
    private static volatile Object result;

    private QueueBenchmark() {}

    /** Печатает среднее время одного выполнения в миллисекундах. */
    public static void main(String[] args) {
        for (int size : new int[]{10_000, 50_000, 100_000}) {
            Integer[] data = new Random(31).ints(size).boxed().toArray(Integer[]::new);
            measure("custom min", size, () -> cycle(data, Comparator.naturalOrder()));
            measure("JDK min", size, () -> standardCycle(data));
            if (size == 100_000) {
                measure("custom max", size, () -> cycle(data, Comparator.reverseOrder()));
                measure("add only", size, () -> build(data));
                measure("heapify", size, () -> new PriorityQueueCustom<>(data));
            }
        }
    }

    private static long cycle(Integer[] data, Comparator<Integer> order) {
        var heap = new PriorityQueueCustom<>(order);
        for (Integer value : data) {
            heap.add(value);
        }
        long checksum = 0;
        while (!heap.isEmpty()) {
            checksum = checksum * 31 + heap.poll();
        }
        return checksum;
    }

    private static long standardCycle(Integer[] data) {
        var heap = new PriorityQueue<Integer>(Comparator.naturalOrder());
        for (Integer value : data) {
            heap.add(value);
        }
        long checksum = 0;
        while (!heap.isEmpty()) {
            checksum = checksum * 31 + heap.poll();
        }
        return checksum;
    }

    private static PriorityQueueCustom<Integer> build(Integer[] data) {
        var heap = new PriorityQueueCustom<Integer>();
        for (Integer value : data) {
            heap.add(value);
        }
        return heap;
    }

    private static void measure(String label, int size, Supplier<?> task) {
        for (int i = 0; i < WARMUP; i++) {
            result = task.get();
        }
        long start = System.nanoTime();
        for (int i = 0; i < REPEATS; i++) {
            result = task.get();
        }
        double milliseconds = (System.nanoTime() - start) / (REPEATS * 1_000_000.0);
        System.out.printf(Locale.ROOT, "%-12s %7d %8.3f ms%n", label, size, milliseconds);
    }
}
