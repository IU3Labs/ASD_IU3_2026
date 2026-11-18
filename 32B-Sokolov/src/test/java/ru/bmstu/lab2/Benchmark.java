package ru.bmstu.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Supplier;

/** Замеры на одних и тех же данных с прогревом JVM. */
public class Benchmark {
    private static final int WARMUP = 3;
    private static final int RUNS = 7;
    private static final int K = 100;
    private static volatile Object result;

    /** Запускает все замеры и печатает средние времена в миллисекундах. */
    public static void main(String[] args) {
        for (int n : new int[] {10_000, 50_000, 100_000}) {
            Integer[] data = numbers(n);
            measure("custom min", n, () -> customCycle(data, Comparator.naturalOrder()));
            measure("Java min", n, () -> standardCycle(data));
            if (n == 100_000) {
                measure("custom max", n, () -> customCycle(data, Comparator.reverseOrder()));
                measure("add", n, () -> build(data));
                measure("heapify", n, () -> new PriorityQueueCustom<>(data));
            }
        }
        List<Integer> input = Arrays.asList(numbers(1_000_000));
        List<Integer> expected = sortedTop(input);
        if (!TopK.topK(input, K).equals(expected)) {
            throw new AssertionError("Top-K differs from sorting");
        }
        measure("Top-K", input.size(), () -> TopK.topK(input, K));
        measure("sort", input.size(), () -> sortedTop(input));
    }

    private static Integer[] numbers(int n) {
        return new Random(42).ints(n).boxed().toArray(Integer[]::new);
    }

    private static List<Integer> sortedTop(List<Integer> input) {
        List<Integer> copy = new ArrayList<>(input);
        copy.sort(Comparator.reverseOrder());
        return List.copyOf(copy.subList(0, K));
    }

    private static PriorityQueueCustom<Integer> build(Integer[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : data) {
            queue.add(value);
        }
        return queue;
    }

    private static long customCycle(Integer[] data, Comparator<Integer> order) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(order);
        for (Integer value : data) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long standardCycle(Integer[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.naturalOrder());
        for (Integer value : data) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static void measure(String label, int n, Supplier<?> action) {
        for (int i = 0; i < WARMUP; i++) {
            result = action.get();
        }
        long start = System.nanoTime();
        for (int i = 0; i < RUNS; i++) {
            result = action.get();
        }
        double ms = (System.nanoTime() - start) / (RUNS * 1_000_000.0);
        System.out.printf(Locale.ROOT, "%s %d %.3f%n", label, n, ms);
    }
}
