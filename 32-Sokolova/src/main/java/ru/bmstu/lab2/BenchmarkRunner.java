package ru.bmstu.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Random;

/**
 * Измерение производительности собственных структур данных.
 */
public final class BenchmarkRunner {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int REPEATS = 5;
    private static volatile long checksum;

    private BenchmarkRunner() {
    }

    /**
     * Запускает сравнительные измерения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        System.out.println("=== ЛР2: Бенчмарки ===");

        // Прогрев JVM перед основными измерениями.
        for (int i = 0; i < 3; i++) {
            int[] warmup = generateData(5_000, 42 + i);
            runCustomQueue(warmup);
            runJavaQueue(warmup);
            runCustomMap(warmup);
            runJavaMap(warmup);
        }

        for (int n : SIZES) {
            int[] data = generateData(n, 42);

            System.out.println("\nN = " + n);

            print("PriorityQueueCustom",
                    measure(() -> runCustomQueue(data)));

            print("java.util.PriorityQueue",
                    measure(() -> runJavaQueue(data)));

            print("HashMapCustom",
                    measure(() -> runCustomMap(data)));

            print("java.util.HashMap",
                    measure(() -> runJavaMap(data)));
        }

        benchmarkTopK();
    }

    private static int[] generateData(int n, long seed) {
        Random random = new Random(seed);
        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1_000_000);
        }

        return data;
    }

    private static double measure(Runnable action) {
        long total = 0;

        for (int i = 0; i < REPEATS; i++) {
            long start = System.nanoTime();
            action.run();
            total += System.nanoTime() - start;
        }

        return total / (double) REPEATS / 1_000_000.0;
    }

    private static void print(String name, double milliseconds) {
        System.out.printf("%-26s %.3f мс%n", name, milliseconds);
    }

    private static void runCustomQueue(int[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        for (int value : data) {
            queue.add(value);
        }

        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        checksum = sum;
    }

    private static void runJavaQueue(int[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int value : data) {
            queue.add(value);
        }

        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        checksum = sum;
    }

    private static void runCustomMap(int[] data) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

        for (int i = 0; i < data.length; i++) {
            map.put(i, data[i]);
        }

        long sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += map.get(i);
        }
        checksum = sum;
    }

    private static void runJavaMap(int[] data) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            map.put(i, data[i]);
        }

        long sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += map.get(i);
        }
        checksum = sum;
    }

    private static void benchmarkTopK() {
        int n = 1_000_000;
        int k = 100;
        int[] data = generateData(n, 123);

        List<Integer> input = new ArrayList<>(n);
        for (int value : data) {
            input.add(value);
        }

        // Прогрев
        TopK.topK(input.subList(0, 10_000), k);

        double topKTime = measure(() -> {
            List<Integer> result = TopK.topK(input, k);
            checksum = result.get(0);
        });

        double sortTime = measure(() -> {
            List<Integer> copy = new ArrayList<>(input);
            copy.sort(java.util.Collections.reverseOrder());
            checksum = copy.get(0);
        });

        System.out.println("\nTop-K: N = " + n + ", k = " + k);
        print("Собственный Top-K", topKTime);
        print("Полная сортировка Java", sortTime);
    }
}
