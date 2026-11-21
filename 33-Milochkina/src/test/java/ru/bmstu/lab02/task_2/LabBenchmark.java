package ru.bmstu.lab02.task_2;

import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import ru.bmstu.lab02.task_3.LabAlgorithms;

/** Снимает воспроизводимые замеры хеш-таблицы и прикладных алгоритмов */
public final class LabBenchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int QUERY_COUNT = 50_000;
    private static final int WARMUP = 5;
    private static final int RUNS = 11;
    private static final int SEED = 33;
    private static final int TOP_COUNT = 100;
    private static volatile Object sink;

    private LabBenchmark() {
    }

    /**
     * Запускает эксперименты по заданиям 2A–3D
     * @param args - аргументы запуска, не используются
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        Integer[] keys = shuffledKeys(SIZES[SIZES.length - 1]);
        mapComparison(keys);
        thresholds(keys);
        collisionComparison();
        algorithms();
    }

    private static Integer[] shuffledKeys(int size) {
        Integer[] keys = new Integer[size];
        for (int index = 0; index < size; index++) {
            // Нечётный множитель задаёт перестановку 32-битных чисел, без дубликатов
            keys[index] = index * 0x9E3779B1;
        }
        Random random = new Random(SEED);
        for (int index = size - 1; index > 0; index--) {
            int other = random.nextInt(index + 1);
            Integer temporary = keys[index];
            keys[index] = keys[other];
            keys[other] = temporary;
        }
        return keys;
    }

    private static Integer[] queries(Integer[] data) {
        Random random = new Random(SEED);
        Integer[] queries = new Integer[QUERY_COUNT];
        for (int index = 0; index < queries.length; index++) {
            queries[index] = index % 2 == 0 ? data[random.nextInt(data.length)] : random.nextInt();
        }
        return queries;
    }

    private static void mapComparison(Integer[] keys) {
        for (int size : SIZES) {
            Integer[] data = Arrays.copyOf(keys, size);
            Integer[] search = queries(data);
            var linear = time(() -> customCycle(data, search, false));
            var quadratic = time(() -> customCycle(data, search, true));
            var standard = time(() -> standardCycle(data, search));
            System.out.printf("maps %d %.3f %.3f %.3f%n", size, linear.ms(), quadratic.ms(), standard.ms());
        }
    }

    private static long customCycle(Integer[] data, Integer[] queries, boolean quadratic) {
        HashMapCustom<Integer, Integer> map = fillCustom(data, quadratic, 0.75);
        long total = 0;
        for (Integer key : queries) {
            Integer value = map.get(key);
            total += value == null ? 0 : value;
        }
        return total;
    }

    private static long standardCycle(Integer[] data, Integer[] queries) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer key : data) {
            map.put(key, key);
        }
        long total = 0;
        for (Integer key : queries) {
            Integer value = map.get(key);
            total += value == null ? 0 : value;
        }
        return total;
    }

    private static HashMapCustom<Integer, Integer> fillCustom(Integer[] keys, boolean quadratic, double load) {
        var map = new HashMapCustom<Integer, Integer>(quadratic, load);
        for (Integer key : keys) {
            map.put(key, key);
        }
        return map;
    }

    private static void thresholds(Integer[] keys) {
        Integer[] search = queries(keys);
        for (boolean quadratic : new boolean[]{false, true}) {
            double probes = averageProbes(fillCustom(keys, quadratic, 0.75), search);
            System.out.printf("probes %s %.4f%n", quadratic, probes);
        }
        for (double load : new double[]{0.5, 0.9}) {
            var map = fillCustom(keys, false, load);
            var put = time(() -> fillCustom(keys, false, load));
            var get = time(() -> lookupAll(map, search));
            System.out.printf("load %.1f %d %.3f %.3f%n", load, map.capacity(),
                    put.ms() * 1_000_000 / keys.length, get.ms() * 1_000_000 / search.length);
        }
    }

    private static long lookupAll(HashMapCustom<Integer, Integer> map, Integer[] search) {
        long total = 0;
        for (Integer key : search) {
            Integer value = map.get(key);
            total += value == null ? 0 : value;
        }
        return total;
    }

    private static <K> double averageProbes(HashMapCustom<K, Integer> map, K[] keys) {
        long probes = 0;
        for (K key : keys) {
            map.get(key);
            probes += map.lastProbes();
        }
        return (double) probes / keys.length;
    }

    private static void collisionComparison() {
        String[] normal = new String[10_000];
        CollisionKey[] collided = new CollisionKey[normal.length];
        for (int index = 0; index < normal.length; index++) {
            normal[index] = Integer.toUnsignedString(index * 0x9E3779B1, 36);
            collided[index] = new CollisionKey(normal[index]);
        }
        for (boolean quadratic : new boolean[]{false, true}) {
            collisionRow(quadratic, "normal", normal);
            collisionRow(quadratic, "constant", collided);
        }
    }

    private static <K> void collisionRow(boolean quadratic, String label, K[] keys) {
        var map = new HashMapCustom<K, Integer>(quadratic);
        for (int index = 0; index < keys.length; index++) {
            map.put(keys[index], index);
        }
        var sample = time(() -> averageProbes(map, keys));
        System.out.printf("collisions %s %s %.4f %.3f%n",
                quadratic, label, averageProbes(map, keys), sample.ms());
    }

    private static void algorithms() {
        Random random = new Random(SEED);
        List<Integer> topInput = new ArrayList<>(1_000_000);
        for (int index = 0; index < 1_000_000; index++) {
            topInput.add(random.nextInt());
        }
        var heap = time(() -> LabAlgorithms.topK(topInput, TOP_COUNT));
        var sorted = time(() -> topBySorting(topInput));
        System.out.printf("top %.3f %.3f%n", heap.ms(), sorted.ms());
        wordExperiment();
        List<Integer> duplicateInput = topInput.stream().limit(100_000).map(value -> value % 20_000).toList();
        var own = time(() -> LabAlgorithms.distinct(duplicateInput));
        var standard = time(() -> new ArrayList<>(new LinkedHashSet<>(duplicateInput)));
        System.out.printf("distinct %.3f %d %.3f %d%n", own.ms(), own.bytes(), standard.ms(), standard.bytes());
        int[] values = random.ints(200_000).toArray();
        var window = time(() -> LabAlgorithms.slidingMinimum(values, 1_000));
        System.out.printf("window %.3f%n", window.ms());
    }

    private static List<Integer> topBySorting(List<Integer> input) {
        List<Integer> sorted = new ArrayList<>(input);
        sorted.sort(Comparator.reverseOrder());
        return new ArrayList<>(sorted.subList(0, TOP_COUNT));
    }

    private static void wordExperiment() {
        String unit = "Лес, ДОМ, кот, лес, ветер, дом, река, небо, дождь, солнце, трава, город. ";
        int copies = 1_000_000 / unit.getBytes(StandardCharsets.UTF_8).length;
        String text = unit.repeat(copies);
        var measured = time(() -> LabAlgorithms.countWords(text));
        System.out.printf("words %d %.3f%n", text.getBytes(StandardCharsets.UTF_8).length, measured.ms());
        LabAlgorithms.countWords(text).entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(10).forEach(entry -> System.out.printf("word %s %d%n", entry.getKey(), entry.getValue()));
    }

    private static Measurement time(Supplier<?> operation) {
        var bean = ManagementFactory.getThreadMXBean();
        var allocation = bean instanceof com.sun.management.ThreadMXBean extended ? extended : null;
        boolean track = allocation != null && allocation.isThreadAllocatedMemorySupported()
                && allocation.isThreadAllocatedMemoryEnabled();
        long threadId = Thread.currentThread().threadId();
        long nanos = 0;
        long bytes = 0;
        for (int round = -WARMUP; round < RUNS; round++) {
            long byteStart = track ? allocation.getThreadAllocatedBytes(threadId) : 0;
            long start = System.nanoTime();
            sink = operation.get();
            long elapsed = System.nanoTime() - start;
            if (round >= 0) {
                nanos += elapsed;
                bytes += track ? allocation.getThreadAllocatedBytes(threadId) - byteStart : 0;
            }
        }
        return new Measurement(nanos / (RUNS * 1_000_000.0), track ? bytes / RUNS : -1);
    }

    private record Measurement(double ms, long bytes) {
    }
}
