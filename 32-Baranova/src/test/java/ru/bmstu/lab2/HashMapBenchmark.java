package ru.bmstu.lab2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

/**
 * Сравнение производительности хеш-таблиц.
 */
public class HashMapBenchmark {

    private static final int REPEATS = 5;
    private static final int WARMUP = 3;

    private static volatile long checksum;

    /**
     * Запускает измерения производительности.
     */
    public static void main(String[] args) {
        int[] sizes = {10000, 50000, 100000};

        for (int size : sizes) {
            Integer[] data = createData(size);
            compareMaps(data);
        }
    }

    private static Integer[] createData(int size) {
        Random random = new Random(42);
        HashSet<Integer> uniqueKeys = new HashSet<>();
        Integer[] data = new Integer[size];

        int i = 0;
        while (i < size) {
            int key = random.nextInt();

            if (uniqueKeys.add(key)) {
                data[i] = key;
                i++;
            }
        }

        return data;
    }

    private static long[] measureCustom(Integer[] data) {
        long start = System.nanoTime();
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

        for (Integer key : data) {
            map.put(key, key);
        }

        long putTime = System.nanoTime() - start;

        long sum = 0;
        start = System.nanoTime();

        for (Integer key : data) {
            sum += map.get(key);
        }

        long getTime = System.nanoTime() - start;

        int removed = 0;
        start = System.nanoTime();

        for (Integer key : data) {
            if (map.remove(key)) {
                removed++;
            }
        }

        long removeTime = System.nanoTime() - start;
        checksum = sum + removed;

        return new long[]{putTime, getTime, removeTime};
    }

    private static long[] measureStandard(Integer[] data) {
        long start = System.nanoTime();
        HashMap<Integer, Integer> map = new HashMap<>();

        for (Integer key : data) {
            map.put(key, key);
        }

        long putTime = System.nanoTime() - start;

        long sum = 0;
        start = System.nanoTime();

        for (Integer key : data) {
            sum += map.get(key);
        }

        long getTime = System.nanoTime() - start;

        int removed = 0;
        start = System.nanoTime();

        for (Integer key : data) {
            if (map.remove(key) != null) {
                removed++;
            }
        }

        long removeTime = System.nanoTime() - start;
        checksum = sum + removed;

        return new long[]{putTime, getTime, removeTime};
    }

    private static void compareMaps(Integer[] data) {
        long[] customTime = new long[3];
        long[] standardTime = new long[3];

        for (int i = 0; i < WARMUP; i++) {
            measureCustom(data);
            measureStandard(data);
        }

        for (int i = 0; i < REPEATS; i++) {
            long[] custom = measureCustom(data);
            long[] standard = measureStandard(data);

            for (int j = 0; j < 3; j++) {
                customTime[j] += custom[j];
                standardTime[j] += standard[j];
            }
        }

        System.out.println("N = " + data.length);
        printResults("Custom", customTime);
        printResults("Standard", standardTime);
    }

    private static void printResults(String name, long[] times) {
        String[] operations = {"put", "get", "remove"};

        for (int i = 0; i < operations.length; i++) {
            double millis = times[i] / REPEATS / 1000000.0;

            System.out.printf(Locale.ROOT, "%s %s: %.3f ms%n",
                    name, operations[i], millis);
        }
    }
}