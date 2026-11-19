package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;

/**
 * Сравнивает время выполнения одинаковых серий операций на своих структурах и структурах JDK.
 */
public class Benchmarks {
    private static final int WARMUP = 8;
    private static final int RUNS = 16;
    private static final int SEED = 33;
    private static final int CHECKSUM_FACTOR = 31;
    private static final double NANOS_PER_MILLISECOND = 1_000_000;
    private static final String[] NAMES = {
        "PriorityQueueCustom (min)", "java.util.PriorityQueue (min)",
        "PriorityQueueCustom (max)", "java.util.PriorityQueue (max)",
        "HashMapCustom", "java.util.HashMap"
    };

    private Benchmarks() {
    }

    /**
     * Запускает сравнение на трёх размерах входа и выводит строки таблицы.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        for (int n : new int[]{10_000, 50_000, 100_000}) {
            Integer[] data = makeData(n);
            Comparator<Integer> reverse = Comparator.reverseOrder();
            LongSupplier[] cases = {
                () -> customQueue(data, null),
                () -> standardQueue(data, null),
                () -> customQueue(data, reverse),
                () -> standardQueue(data, reverse),
                () -> customMap(data),
                () -> standardMap(data)
            };
            measure(n, cases);
        }
    }

    private static Integer[] makeData(int n) {
        Random random = new Random(SEED);
        HashSet<Integer> seen = new HashSet<>();
        Integer[] data = new Integer[n];
        int i = 0;
        while (i < n) {
            int x = random.nextInt();
            if (seen.add(x)) {
                data[i++] = x;
            }
        }
        return data;
    }

    private static void measure(int n, LongSupplier[] cases) {
        long[] expected = referenceResults(cases);
        double[] times = new double[cases.length];

        for (int run = 0; run < WARMUP + RUNS; run++) {
            // Сдвигаем порядок вариантов, чтобы один и тот же вариант не запускался первым.
            for (int step = 0; step < cases.length; step++) {
                int i = (run + step) % cases.length;
                long start = System.nanoTime();
                long result = cases[i].getAsLong();
                long elapsed = System.nanoTime() - start;
                if (result != expected[i]) {
                    throw new IllegalStateException(NAMES[i]);
                }
                if (run >= WARMUP) {
                    times[i] += elapsed;
                }
            }
        }
        for (int i = 0; i < cases.length; i++) {
            String operation = i < cases.length - 2 ? "add + poll" : "put + get + remove";
            System.out.printf(Locale.ROOT, "| %s | %s | %,d | %.3f |%n",
                    NAMES[i], operation, n, times[i] / RUNS / NANOS_PER_MILLISECOND);
        }
    }

    private static long[] referenceResults(LongSupplier[] cases) {
        long[] expected = new long[cases.length];
        for (int i = 0; i < cases.length; i += 2) {
            expected[i] = cases[i + 1].getAsLong();
            expected[i + 1] = expected[i];
        }
        return expected;
    }

    private static long customQueue(Integer[] data, Comparator<Integer> comparator) {
        var queue = new PriorityQueueCustom<Integer>(comparator);
        for (Integer x : data) {
            queue.add(x);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum = sum * CHECKSUM_FACTOR + queue.poll();
        }
        return sum;
    }

    private static long standardQueue(Integer[] data, Comparator<Integer> comparator) {
        var queue = new PriorityQueue<Integer>(comparator);
        for (Integer x : data) {
            queue.add(x);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum = sum * CHECKSUM_FACTOR + queue.poll();
        }
        return sum;
    }

    private static long customMap(Integer[] data) {
        var map = new HashMapCustom<Integer, Integer>();
        for (Integer x : data) {
            map.put(x, x);
        }
        long sum = 0;
        for (Integer x : data) {
            sum += map.get(x);
        }
        for (Integer x : data) {
            if (map.remove(x)) {
                sum++;
            }
        }
        return sum;
    }

    private static long standardMap(Integer[] data) {
        var map = new HashMap<Integer, Integer>();
        for (Integer x : data) {
            map.put(x, x);
        }
        long sum = 0;
        for (Integer x : data) {
            sum += map.get(x);
        }
        for (Integer x : data) {
            if (map.remove(x) != null) {
                sum++;
            }
        }
        return sum;
    }
}
