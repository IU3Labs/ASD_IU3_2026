package ru.bmstu.lab2.bench;

import java.util.HashMap;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;
import ru.bmstu.lab2.hash.HashMapCustom;
import ru.bmstu.lab2.heap.PriorityQueueCustom;

/** воспроизводимые сравнения структур на одинаковых входных массивах */
public final class Benchmark {
    private static final int REPETITIONS = 10;
    private static volatile Object retained;
    private static volatile long checksum;

    private Benchmark() {
    }

    /** печатает среднее время серии в миллисекундах */
    public static void main(String[] args) {
        for (int n : new int[] {10_000, 50_000, 100_000}) {
            Integer[] data = new Random(123).ints(n).boxed().toArray(Integer[]::new);
            measure("PriorityQueueCustom", "add + poll", n, () -> heap(data, false));
            measure("PriorityQueue", "add + poll", n, () -> heap(data, true));
            measure("HashMapCustom", "put + get + remove", n, () -> table(data, false));
            measure("HashMap", "put + get + remove", n, () -> table(data, true));
            if (n == 100_000) {
                measure("PriorityQueueCustom", "add", n, () -> build(data, false));
                measure("PriorityQueueCustom", "heapify", n, () -> build(data, true));
            }
        }
    }

    private static long build(Integer[] data, boolean bottomUp) {
        PriorityQueueCustom<Integer> queue;
        if (bottomUp) {
            queue = new PriorityQueueCustom<>(data);
        } else {
            queue = new PriorityQueueCustom<>();
            for (Integer value : data) {
                queue.add(value);
            }
        }
        retained = queue;
        return queue.peek();
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

    private static long table(Integer[] data, boolean library) {
        long sum = 0;
        if (library) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (Integer value : data) {
                map.put(value, value);
            }
            for (Integer value : data) {
                sum += map.get(value);
            }
            for (Integer value : data) {
                map.remove(value);
            }
        } else {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
            for (Integer value : data) {
                map.put(value, value);
            }
            for (Integer value : data) {
                sum += map.get(value);
            }
            for (Integer value : data) {
                map.remove(value);
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
