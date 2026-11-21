package ru.bmstu.lab02;

import java.util.HashMap;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;

/** Runs the timing experiments for lab 2 without coverage instrumentation. */
public final class Lab02Benchmark {

    private static final int WARMUP_ROUNDS = 10;
    private static final int MEASURED_ROUNDS = 30;
    private static final int LARGEST_SIZE = 100_000;
    private static final int HASH_MULTIPLIER = 0x9E3779B9;
    private static volatile long result;

    private Lab02Benchmark() {
    }

    /** Prints Markdown rows with arithmetic mean timings in milliseconds. */
    public static void main(String[] args) {
        for (int size : new int[]{10_000, 50_000, LARGEST_SIZE}) {
            Integer[] values = createShuffledKeys(size);
            compare("PriorityQueueCustom", "java.util.PriorityQueue", "add + poll", size,
                    () -> runCustomQueue(values), () -> runStandardQueue(values));
            compare("HashMapCustom", "java.util.HashMap", "put + get + remove", size,
                    () -> runCustomMap(values), () -> runStandardMap(values));
        }
        Integer[] values = createShuffledKeys(LARGEST_SIZE);
        compare("PriorityQueueCustom, последовательное построение", "PriorityQueueCustom, снизу вверх",
                "построение", values.length, () -> buildByAdding(values), () -> buildFromArray(values));
    }

    private static Integer[] createShuffledKeys(int size) {
        Integer[] values = new Integer[size];
        for (int index = 0; index < size; index++) {
            values[index] = index * HASH_MULTIPLIER;
        }
        Random random = new Random(2026);
        for (int index = size - 1; index > 0; index--) {
            int other = random.nextInt(index + 1);
            Integer saved = values[index];
            values[index] = values[other];
            values[other] = saved;
        }
        return values;
    }

    private static void compare(String firstName, String secondName, String operation, int size,
                                LongSupplier first, LongSupplier second) {
        for (int round = 0; round < WARMUP_ROUNDS; round++) {
            result = first.getAsLong();
            result = second.getAsLong();
        }
        long firstTotal = 0;
        long secondTotal = 0;
        for (int round = 0; round < MEASURED_ROUNDS; round++) {
            if (round % 2 == 0) {
                firstTotal += measure(first);
                secondTotal += measure(second);
            } else {
                secondTotal += measure(second);
                firstTotal += measure(first);
            }
        }
        printRow(firstName, operation, size, firstTotal);
        printRow(secondName, operation, size, secondTotal);
    }

    private static long measure(LongSupplier workload) {
        long start = System.nanoTime();
        long checksum = workload.getAsLong();
        long elapsed = System.nanoTime() - start;
        result = checksum;
        return elapsed;
    }

    private static void printRow(String name, String operation, int size, long elapsed) {
        double mean = elapsed / (MEASURED_ROUNDS * 1_000_000.0);
        System.out.printf(Locale.ROOT, "| %s | %s | %d | %.3f | %d повторов |%n",
                name, operation, size, mean, MEASURED_ROUNDS);
    }

    private static long runCustomQueue(Integer[] values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : values) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
    }

    private static long runStandardQueue(Integer[] values) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer value : values) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
    }

    private static long runCustomMap(Integer[] keys) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        long checksum = 0;
        for (Integer key : keys) {
            checksum += map.get(key);
        }
        for (Integer key : keys) {
            checksum += map.remove(key) ? 1 : 0;
        }
        return checksum + map.size();
    }

    private static long runStandardMap(Integer[] keys) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        long checksum = 0;
        for (Integer key : keys) {
            checksum += map.get(key);
        }
        for (Integer key : keys) {
            checksum += map.remove(key) != null ? 1 : 0;
        }
        return checksum + map.size();
    }

    private static long buildByAdding(Integer[] values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : values) {
            queue.add(value);
        }
        return queue.size() + (long) queue.peek();
    }

    private static long buildFromArray(Integer[] values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);
        return queue.size() + (long) queue.peek();
    }
}
