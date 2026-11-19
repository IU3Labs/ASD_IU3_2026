package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

// Сравнение производительности структур Лабораторной работы №2 со стандартной библиотекой.
public class Lab02Benchmarks {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int LARGE_SIZE = 100_000;
    private static final int WARMUP_RUNS = 2;
    private static final int MEASURE_RUNS = 5;
    private static final long SEED = 42L;

    /**
     * Точка входа для запуска всех бенчмарков.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        System.out.println("=== 1. PriorityQueueCustom vs java.util.PriorityQueue (add + poll) ===");
        runPriorityQueueBenchmarks();

        System.out.println("\n=== 2. HashMapCustom vs java.util.HashMap (put + get) ===");
        runHashMapBenchmarks();

        System.out.println("\n=== 3. Task 1B: min-heap vs max-heap (100 000 elements) ===");
        runMinVsMaxBenchmark();

        System.out.println("\n=== 4. Task 1D: sequential add vs O(n) constructor (100 000 elements) ===");
        runBuildHeapBenchmark();
    }

    private static void runPriorityQueueBenchmarks() {
        for (int size : SIZES) {
            Integer[] data = generateRandomArray(size);
            double customMs = measureCustomPq(data, null);
            double stdMs = measureStdPq(data);
            printRow("PriorityQueueCustom", "add + poll", size, customMs);
            printRow("java.util.PriorityQueue", "add + poll", size, stdMs);
        }
    }

    private static void runHashMapBenchmarks() {
        for (int size : SIZES) {
            Integer[] data = generateRandomArray(size);
            double customMs = measureCustomMap(data);
            double stdMs = measureStdMap(data);
            printRow("HashMapCustom", "put + get", size, customMs);
            printRow("java.util.HashMap", "put + get", size, stdMs);
        }
    }

    private static void runMinVsMaxBenchmark() {
        Integer[] data = generateRandomArray(LARGE_SIZE);
        double minNaturalMs = measureCustomPq(data, null);
        double minCompMs = measureCustomPq(data, Comparator.<Integer>naturalOrder());
        double maxCompMs = measureCustomPq(data, Comparator.<Integer>reverseOrder());

        printRow("PriorityQueueCustom (min, natural)", "add + poll", LARGE_SIZE, minNaturalMs);
        printRow("PriorityQueueCustom (min, Comparator)", "add + poll", LARGE_SIZE, minCompMs);
        printRow("PriorityQueueCustom (max, Comparator)", "add + poll", LARGE_SIZE, maxCompMs);
    }

    private static void runBuildHeapBenchmark() {
        Integer[] data = generateRandomArray(LARGE_SIZE);
        double seqMs = measureBuildSequential(data);
        double linearMs = measureBuildLinear(data);

        printRow("PriorityQueueCustom (sequential add)", "build O(n log n)", LARGE_SIZE, seqMs);
        printRow("PriorityQueueCustom (array ctor)", "build O(n)", LARGE_SIZE, linearMs);
    }

    private static double measureCustomPq(Integer[] data, Comparator<Integer> comparator) {
        long totalNs = 0;
        for (int r = 0; r < WARMUP_RUNS + MEASURE_RUNS; r++) {
            long start = System.nanoTime();
            PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(comparator);
            for (int i = 0; i < data.length; i++) {
                pq.add(data[i]);
            }
            while (!pq.isEmpty()) {
                pq.poll();
            }
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                totalNs += elapsed;
            }
        }
        return (totalNs / (double) MEASURE_RUNS) / 1_000_000.0;
    }

    private static double measureStdPq(Integer[] data) {
        long totalNs = 0;
        for (int r = 0; r < WARMUP_RUNS + MEASURE_RUNS; r++) {
            long start = System.nanoTime();
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int i = 0; i < data.length; i++) {
                pq.add(data[i]);
            }
            while (!pq.isEmpty()) {
                pq.poll();
            }
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                totalNs += elapsed;
            }
        }
        return (totalNs / (double) MEASURE_RUNS) / 1_000_000.0;
    }

    private static double measureCustomMap(Integer[] data) {
        long totalNs = 0;
        for (int r = 0; r < WARMUP_RUNS + MEASURE_RUNS; r++) {
            long start = System.nanoTime();
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
            for (int i = 0; i < data.length; i++) {
                map.put(data[i], i);
            }
            for (int i = 0; i < data.length; i++) {
                map.get(data[i]);
            }
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                totalNs += elapsed;
            }
        }
        return (totalNs / (double) MEASURE_RUNS) / 1_000_000.0;
    }

    private static double measureStdMap(Integer[] data) {
        long totalNs = 0;
        for (int r = 0; r < WARMUP_RUNS + MEASURE_RUNS; r++) {
            long start = System.nanoTime();
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < data.length; i++) {
                map.put(data[i], i);
            }
            for (int i = 0; i < data.length; i++) {
                map.get(data[i]);
            }
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                totalNs += elapsed;
            }
        }
        return (totalNs / (double) MEASURE_RUNS) / 1_000_000.0;
    }

    private static double measureBuildSequential(Integer[] data) {
        long totalNs = 0;
        for (int r = 0; r < WARMUP_RUNS + MEASURE_RUNS; r++) {
            long start = System.nanoTime();
            PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
            for (int i = 0; i < data.length; i++) {
                pq.add(data[i]);
            }
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                totalNs += elapsed;
            }
        }
        return (totalNs / (double) MEASURE_RUNS) / 1_000_000.0;
    }

    private static double measureBuildLinear(Integer[] data) {
        long totalNs = 0;
        for (int r = 0; r < WARMUP_RUNS + MEASURE_RUNS; r++) {
            long start = System.nanoTime();
            PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(data);
            pq.peek();
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                totalNs += elapsed;
            }
        }
        return (totalNs / (double) MEASURE_RUNS) / 1_000_000.0;
    }

    private static Integer[] generateRandomArray(int size) {
        Random random = new Random(SEED);
        Integer[] data = new Integer[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    private static void printRow(String name, String op, int size, double avgMs) {
        System.out.printf(Locale.US, "%-40s | %-16s | %7d | %8.2f ms%n", name, op, size, avgMs);
    }
}