package ru.bmstu.lab2.bench;

import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;
import ru.bmstu.lab2.heap.PriorityQueueCustom;

/** воспроизводимые сравнения структур на одинаковых входных массивах */
public final class Benchmark {
    private static final int REPETITIONS = 10;
    private static volatile long checksum;

    private Benchmark() {
    }

    /** печатает среднее время серии в миллисекундах */
    public static void main(String[] args) {
        for (int n : new int[] {10_000, 50_000, 100_000}) {
            Integer[] data = new Random(123).ints(n).boxed().toArray(Integer[]::new);
            measure("PriorityQueueCustom", "add + poll", n, () -> heap(data, false));
            measure("PriorityQueue", "add + poll", n, () -> heap(data, true));
        }
    }

    private static long heap(Integer[] data, boolean library) {
        long sum = 0;
        if (library) {
            PriorityQueue<Integer> queue = new PriorityQueue<>();
            for (Integer value : data) {
                queue.add(value);
            }
            while (!queue.isEmpty()) {
                sum += queue.poll();
            }
        } else {
            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
            for (Integer value : data) {
                queue.add(value);
            }
            while (!queue.isEmpty()) {
                sum += queue.poll();
            }
        }
        return sum;
    }

    private static void measure(String name, String operation, int n, LongSupplier run) {
        for (int i = 0; i < 5; i++) {
            checksum = run.getAsLong();
        }
        long start = System.nanoTime();
        for (int i = 0; i < REPETITIONS; i++) {
            checksum = run.getAsLong();
        }
        double milliseconds = (System.nanoTime() - start) / (1_000_000.0 * REPETITIONS);
        System.out.printf(Locale.ROOT, "| %s | %s | %d | %.3f |%n", name, operation, n, milliseconds);
    }
}
