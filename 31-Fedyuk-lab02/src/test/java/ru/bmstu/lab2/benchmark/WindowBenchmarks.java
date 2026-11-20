package ru.bmstu.lab2.benchmark;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import ru.bmstu.lab2.task_3.SlidingMinimum;

/** Задача 3D: сравнение одного алгоритма с собственной и стандартной очередями. */
final class WindowBenchmarks {
    private static final int SIZE = 200_000;
    private static final int WINDOW = 1_000;
    private static final long SEED = 20261120L;

    private WindowBenchmarks() {}

    static void run() {
        int[] values = new Random(SEED).ints(SIZE).toArray();
        int[] expected = standardMinimum(values);
        if (!Arrays.equals(expected, SlidingMinimum.solve(values, WINDOW))) {
            throw new AssertionError("Результаты скользящего минимума различаются");
        }
        long checksum = Arrays.stream(expected).asLongStream().sum();
        BenchmarkTimer.measure(
                "window",
                "minimum-k1000",
                "PriorityQueueCustom",
                SIZE,
                () ->
                        () ->
                                Arrays.stream(SlidingMinimum.solve(values, WINDOW))
                                        .asLongStream()
                                        .sum(),
                checksum);
        BenchmarkTimer.measure(
                "window",
                "minimum-k1000",
                "java.util.PriorityQueue",
                SIZE,
                () -> () -> Arrays.stream(standardMinimum(values)).asLongStream().sum(),
                checksum);
    }

    private static int[] standardMinimum(int[] values) {
        PriorityQueue<BenchmarkEntry> queue =
                new PriorityQueue<>(
                        Comparator.comparingInt(BenchmarkEntry::value)
                                .thenComparingInt(BenchmarkEntry::index));
        int[] result = new int[values.length - WINDOW + 1];
        for (int index = 0; index < values.length; index++) {
            queue.add(new BenchmarkEntry(values[index], index));
            int left = index - WINDOW + 1;
            while (queue.peek().index() < left) {
                queue.poll();
            }
            if (left >= 0) {
                result[left] = queue.peek().value();
            }
        }
        return result;
    }
}
