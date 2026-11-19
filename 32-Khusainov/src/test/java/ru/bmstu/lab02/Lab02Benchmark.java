package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;

/** Воспроизводимые замеры полных серий операций. Запуск отдельным main после test-compile. */
public final class Lab02Benchmark {
    private static final int WARMUPS = 3;
    private static final int RUNS = 7;
    private static final int K = 100;
    private static final long SEED = 2026;
    private static volatile long sink;

    private Lab02Benchmark() {
    }

    /**
     * Печатает таблицу в Markdown. Подготовка входов и проверка Top-K вынесены из замеров.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N | Среднее, мс |");
        System.out.println("|---|---|---:|---:|");
        for (int size : new int[]{10_000, 50_000, 100_000}) {
            List<Integer> keys = new Random(SEED).ints().distinct().limit(size).boxed().toList();
            measure("PriorityQueueCustom", "add + poll", size, () -> customHeap(keys));
            measure("PriorityQueue", "add + poll", size, () -> standardHeap(keys));
            measure("HashMapCustom", "put + get + remove", size, () -> customMap(keys));
            measure("HashMap", "put + get + remove", size, () -> standardMap(keys));
        }
        List<Integer> data = new Random(SEED).ints(1_000_000).boxed().toList();
        if (!TopK.topK(data, K).equals(sortedTop(data))) {
            throw new AssertionError("Top-K differs from sorting");
        }
        measure("TopK", "k = " + K, data.size(), () -> TopK.topK(data, K).hashCode());
        measure("ArrayList.sort", "сортировка копии и первые " + K, data.size(), () -> sortedTop(data).hashCode());
    }

    private static List<Integer> sortedTop(List<Integer> data) {
        List<Integer> copy = new ArrayList<>(data);
        copy.sort(Comparator.reverseOrder());
        return copy.subList(0, K);
    }

    private static void measure(String name, String operation, int size, LongSupplier run) {
        long elapsed = 0;
        for (int i = -WARMUPS; i < RUNS; i++) {
            long start = System.nanoTime();
            long result = run.getAsLong();
            long duration = System.nanoTime() - start;
            sink = result;
            if (i >= 0) {
                elapsed += duration;
            }
        }
        System.out.printf(Locale.ROOT, "| %s | %s | %d | %.3f |%n", name, operation, size,
                elapsed / (RUNS * 1_000_000.0));
    }

    private static long customHeap(List<Integer> keys) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer key : keys) {
            queue.add(key);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long standardHeap(List<Integer> keys) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer key : keys) {
            queue.add(key);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long customMap(List<Integer> keys) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        long sum = 0;
        for (Integer key : keys) {
            sum += map.get(key);
        }
        for (Integer key : keys) {
            map.remove(key);
        }
        return sum + map.size();
    }

    private static long standardMap(List<Integer> keys) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        long sum = 0;
        for (Integer key : keys) {
            sum += map.get(key);
        }
        for (Integer key : keys) {
            map.remove(key);
        }
        return sum + map.size();
    }
}
