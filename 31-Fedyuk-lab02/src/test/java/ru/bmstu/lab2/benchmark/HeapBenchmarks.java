package ru.bmstu.lab2.benchmark;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import ru.bmstu.lab2.task_1.PriorityQueueCustom;

/** Задачи 1A, 1B и 1D: цикл очереди, направления порядка и способы построения. */
final class HeapBenchmarks {
    private static final int LARGE_SIZE = 100_000;
    private static final long SEED = 20261120L;

    private HeapBenchmarks() {}

    static void run() {
        for (int size : new int[] {10_000, 50_000, LARGE_SIZE}) {
            Integer[] input = new Random(SEED).ints(size).boxed().toArray(Integer[]::new);
            measureCycle("heap", input, Comparator.naturalOrder(), "min");
            if (size == LARGE_SIZE) {
                measureCycle("heap-mode", input, Comparator.reverseOrder(), "max");
                measureConstruction(input);
            }
        }
    }

    private static void measureCycle(
            String group, Integer[] input, Comparator<Integer> comparator, String mode) {
        long expected = Arrays.stream(input).mapToLong(Integer::longValue).sum();
        BenchmarkTimer.measure(
                group,
                "add+poll-" + mode,
                "PriorityQueueCustom",
                input.length,
                () -> () -> customCycle(input, comparator),
                expected);
        BenchmarkTimer.measure(
                group,
                "add+poll-" + mode,
                "java.util.PriorityQueue",
                input.length,
                () -> () -> standardCycle(input, comparator),
                expected);
    }

    private static long customCycle(Integer[] input, Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        for (Integer value : input) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long standardCycle(Integer[] input, Comparator<Integer> comparator) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(comparator);
        for (Integer value : input) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static void measureConstruction(Integer[] input) {
        long expected =
                input.length + (long) Arrays.stream(input).min(Integer::compareTo).orElseThrow();
        BenchmarkTimer.measure(
                "heap-build",
                "repeated-add",
                "PriorityQueueCustom",
                input.length,
                () -> () -> buildByAdding(input),
                expected);
        BenchmarkTimer.measure(
                "heap-build",
                "heapify",
                "PriorityQueueCustom",
                input.length,
                () ->
                        () -> {
                            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input);
                            return queue.size() + (long) queue.peek();
                        },
                expected);
        measureStandardConstruction(input, expected);
    }

    private static void measureStandardConstruction(Integer[] input, long expected) {
        List<Integer> values = Arrays.asList(input);
        BenchmarkTimer.measure(
                "heap-build",
                "heapify",
                "java.util.PriorityQueue",
                input.length,
                () ->
                        () -> {
                            PriorityQueue<Integer> queue = new PriorityQueue<>(values);
                            return queue.size() + (long) queue.peek();
                        },
                expected);
    }

    private static long buildByAdding(Integer[] input) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : input) {
            queue.add(value);
        }
        return queue.size() + (long) queue.peek();
    }
}
