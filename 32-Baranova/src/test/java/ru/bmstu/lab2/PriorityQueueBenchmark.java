package ru.bmstu.lab2;

import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Сравнение производительности очередей с приоритетом.
 */
public class PriorityQueueBenchmark {

    private static final int REPEATS = 5;
    private static final int WARMUP = 3;

    private static volatile long checksum;
    private static volatile Object builtQueue;

    /**
     * Запускает измерения производительности.
     */
    public static void main(String[] args) {
        int[] sizes = {10000, 50000, 100000};

        for (int size : sizes) {
            Integer[] data = createData(size);
            compareQueues(data);
        }

        compareBuild(createData(100000));
    }

    private static Integer[] createData(int size) {
        Random random = new Random(42);
        Integer[] data = new Integer[size];

        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(1000000);
        }

        return data;
    }

    private static long measureCustom(Integer[] data) {
        long start = System.nanoTime();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        for (Integer value : data) {
            queue.add(value);
        }

        long sum = 0;

        while (!queue.isEmpty()) {
            sum += queue.poll();
        }

        checksum = sum;
        return System.nanoTime() - start;
    }

    private static long measureStandard(Integer[] data) {
        long start = System.nanoTime();
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (Integer value : data) {
            queue.add(value);
        }

        long sum = 0;

        while (!queue.isEmpty()) {
            sum += queue.poll();
        }

        checksum = sum;
        return System.nanoTime() - start;
    }

    private static void compareQueues(Integer[] data) {
        long customTime = 0;
        long standardTime = 0;

        for (int i = 0; i < WARMUP; i++) {
            measureCustom(data);
            measureStandard(data);
        }

        for (int i = 0; i < REPEATS; i++) {
            customTime += measureCustom(data);
            standardTime += measureStandard(data);
        }

        System.out.println("N = " + data.length);

        System.out.printf(Locale.ROOT, "Custom add + poll: %.3f ms%n",
                toMillis(customTime));
        System.out.printf(Locale.ROOT, "Standard add + poll: %.3f ms%n",
                toMillis(standardTime));
    }

    private static long buildByAdd(Integer[] data) {
        long start = System.nanoTime();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        for (Integer value : data) {
            queue.add(value);
        }

        builtQueue = queue;
        return System.nanoTime() - start;
    }

    private static long buildFromArray(Integer[] data) {
        long start = System.nanoTime();
        builtQueue = new PriorityQueueCustom<>(data);

        return System.nanoTime() - start;
    }

    private static void compareBuild(Integer[] data) {
        long addTime = 0;
        long arrayTime = 0;

        for (int i = 0; i < WARMUP; i++) {
            buildByAdd(data);
            buildFromArray(data);
        }

        for (int i = 0; i < REPEATS; i++) {
            addTime += buildByAdd(data);
            arrayTime += buildFromArray(data);
        }

        System.out.println("Task 1D, N = " + data.length);

        System.out.printf(Locale.ROOT, "Build by add: %.3f ms%n",
                toMillis(addTime));
        System.out.printf(Locale.ROOT, "Build from array: %.3f ms%n",
                toMillis(arrayTime));
    }

    private static double toMillis(long totalTime) {
        return totalTime / REPEATS / 1000000.0;
    }
}