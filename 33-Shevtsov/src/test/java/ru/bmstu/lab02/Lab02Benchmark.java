package ru.bmstu.lab02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.function.LongSupplier;

/** Сравнивает структуры курса со стандартными и измеряет два способа построения кучи. */
public final class Lab02Benchmark {

    private static final int HEAP_BUILD_SIZE = 100_000;
    private static final int WARMUP_ROUNDS = 8;
    private static final int MEASURED_ROUNDS = 12;
    private static final long SEED = 33;

    private Lab02Benchmark() {
    }

    /** Запускает замеры и печатает средние времена в формате CSV. */
    public static void main(String[] args) {
        System.out.println("N,workload,mean_ms");
        for (int size : new int[] {10_000, 50_000, HEAP_BUILD_SIZE}) {
            Integer[] keys = createKeys(size);
            long sum = 0;
            int minimum = Integer.MAX_VALUE;
            for (Integer key : keys) {
                sum += key;
                minimum = Math.min(minimum, key);
            }
            runSeries(size, sum,
                    new Workload("custom_heap_add_poll", () -> customQueueCycle(keys)),
                    new Workload("java_heap_add_poll", () -> standardQueueCycle(keys)),
                    new Workload("custom_map_put_get_remove", () -> customMapCycle(keys)),
                    new Workload("java_map_put_get_remove", () -> standardMapCycle(keys)));
            if (size == HEAP_BUILD_SIZE) {
                runSeries(size, (long) minimum + size,
                        new Workload("custom_heap_build_add", () -> buildByAdding(keys)),
                        new Workload("custom_heap_build_heapify", () -> buildByHeapifying(keys)));
            }
        }
    }

    private static Integer[] createKeys(int size) {
        Random random = new Random(SEED);
        Set<Integer> seen = new HashSet<>();
        Integer[] keys = new Integer[size];
        int index = 0;
        while (index < size) {
            Integer key = random.nextInt();
            if (seen.add(key)) {
                keys[index++] = key;
            }
        }
        return keys;
    }

    private static void runSeries(int size, long expected, Workload... workloads) {
        long[] elapsed = new long[workloads.length];
        for (int round = 0; round < WARMUP_ROUNDS + MEASURED_ROUNDS; round++) {
            for (int offset = 0; offset < workloads.length; offset++) {
                int index = (round + offset) % workloads.length;
                long start = System.nanoTime();
                long result = workloads[index].run().getAsLong();
                long duration = System.nanoTime() - start;
                if (result != expected) {
                    throw new IllegalStateException("Неверная контрольная сумма: " + workloads[index].name());
                }
                if (round >= WARMUP_ROUNDS) {
                    elapsed[index] += duration;
                }
            }
        }
        for (int index = 0; index < workloads.length; index++) {
            double meanMillis = elapsed[index] / (MEASURED_ROUNDS * 1_000_000.0);
            System.out.printf(Locale.ROOT, "%d,%s,%.3f%n", size, workloads[index].name(), meanMillis);
        }
    }

    private static long customQueueCycle(Integer[] keys) {
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

    private static long standardQueueCycle(Integer[] keys) {
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

    private static long customMapCycle(Integer[] keys) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        long sum = 0;
        for (Integer key : keys) {
            sum += map.get(key);
        }
        for (Integer key : keys) {
            if (!map.remove(key)) {
                throw new IllegalStateException("Ключ исчез до удаления");
            }
        }
        return sum + map.size();
    }

    private static long standardMapCycle(Integer[] keys) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        long sum = 0;
        for (Integer key : keys) {
            sum += map.get(key);
        }
        for (Integer key : keys) {
            if (map.remove(key) == null) {
                throw new IllegalStateException("Ключ исчез до удаления");
            }
        }
        return sum + map.size();
    }

    private static long buildByAdding(Integer[] keys) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer key : keys) {
            queue.add(key);
        }
        return (long) queue.peek() + queue.size();
    }

    private static long buildByHeapifying(Integer[] keys) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(keys);
        return (long) queue.peek() + queue.size();
    }

    private record Workload(String name, LongSupplier run) {
    }
}
