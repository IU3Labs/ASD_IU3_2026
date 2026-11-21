package ru.bmstu.lab2.task_2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/** Воспроизводимые замеры для задачи 2A. */
public final class HashMapBenchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int QUERY_COUNT = 50_000;
    private static final int WARMUPS = 3;
    private static final int RUNS = 5;
    private static final int SEED = 2026;

    private HashMapBenchmark() {}

    /**
     * Запускает замеры и печатает результаты в табличном формате.
     *
     * @param args аргументы не используются
     */
    public static void main(String[] args) {
        Integer[] pool = uniqueKeys(SIZES[SIZES.length - 1] + QUERY_COUNT / 2);
        for (int size : SIZES) {
            Workload<Integer> workload = workload(pool, size);
            printTiming("jdk", workload, HashMapBenchmark::jdkMap);
            printTiming("linear-0.75", workload, HashMapBenchmark::customMap);
        }
    }

    private static Integer[] uniqueKeys(int count) {
        Integer[] keys = new Integer[count];
        HashSet<Integer> used = new HashSet<>();
        Random random = new Random(SEED);
        int filled = 0;
        while (filled < keys.length) {
            Integer key = random.nextInt();
            if (used.add(key)) {
                keys[filled++] = key;
            }
        }
        return keys;
    }

    private static Workload<Integer> workload(Integer[] pool, int size) {
        Integer[] keys = Arrays.copyOf(pool, size);
        Integer[] queries = new Integer[QUERY_COUNT];
        Random random = new Random(SEED + size);
        for (int i = 0; i < queries.length; i += 2) {
            queries[i] = keys[random.nextInt(keys.length)];
            queries[i + 1] = pool[size + i / 2];
        }
        return new Workload<>(keys, queries, queries.length / 2);
    }

    private static <K> void printTiming(
            String label, Workload<K> workload, Supplier<TimedMap<K>> factory) {
        Timing timing = measure(workload, factory);
        System.out.printf(
                Locale.ROOT,
                "TIME\t%s\t%d\t%d\t%.3f\t%.3f\t%d%n",
                label,
                workload.keys().length,
                workload.queries().length,
                timing.putMillis(),
                timing.getMillis(),
                timing.capacity());
    }

    private static <K> Timing measure(Workload<K> workload, Supplier<TimedMap<K>> factory) {
        long putNanos = 0;
        long getNanos = 0;
        int capacity = 0;
        for (int trial = 0; trial < WARMUPS + RUNS; trial++) {
            TimedMap<K> map = factory.get();
            long start = System.nanoTime();
            for (K key : workload.keys()) {
                map.put().accept(key, 1);
            }
            long inserted = System.nanoTime();
            int hits = countHits(map.get(), workload.queries());
            long searched = System.nanoTime();
            if (hits != workload.expectedHits()) {
                throw new IllegalStateException("Неверное число найденных ключей");
            }
            if (trial >= WARMUPS) {
                putNanos += inserted - start;
                getNanos += searched - inserted;
            }
            capacity = map.capacity().getAsInt();
        }
        double nanosPerMillis = 1_000_000.0;
        return new Timing(
                putNanos / RUNS / nanosPerMillis, getNanos / RUNS / nanosPerMillis, capacity);
    }

    private static <K> int countHits(Function<K, Integer> get, K[] queries) {
        int hits = 0;
        for (K key : queries) {
            Integer value = get.apply(key);
            if (value != null) {
                hits += value;
            }
        }
        return hits;
    }

    private static <K> TimedMap<K> customMap() {
        HashMapCustom<K, Integer> table = new HashMapCustom<>();
        return new TimedMap<>(table::put, table::get, table::capacity);
    }

    private static <K> TimedMap<K> jdkMap() {
        HashMap<K, Integer> table = new HashMap<>();
        return new TimedMap<>(table::put, table::get, () -> 0);
    }

    private record Workload<K>(K[] keys, K[] queries, int expectedHits) {}

    private record TimedMap<K>(
            BiConsumer<K, Integer> put, Function<K, Integer> get, IntSupplier capacity) {}

    private record Timing(double putMillis, double getMillis, int capacity) {}
}
