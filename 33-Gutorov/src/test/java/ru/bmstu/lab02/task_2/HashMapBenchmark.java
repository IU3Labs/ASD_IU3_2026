package ru.bmstu.lab02.task_2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

/** Сравнивает время вставки и поиска в двух хеш-таблицах. */
public final class HashMapBenchmark {

    private static final int WARMUP_ROUNDS = 3;
    private static final int MEASURED_ROUNDS = 8;
    private static final int INITIAL_CAPACITY = 8;
    private static final long SEED = 42L;
    private static final double NANOS_PER_MILLISECOND = 1_000_000.0;

    private HashMapBenchmark() {
    }

    /** Запускает замеры для трёх размеров входных данных. */
    public static void main(String[] args) {
        System.out.println("| Элементов | HashMapCustom, мс | HashMap, мс |");
        System.out.println("|---:|---:|---:|");
        for (int size : new int[] {10_000, 50_000, 100_000}) {
            compare(size);
        }
    }

    private static void compare(int size) {
        Integer[] keys = generateKeys(size);
        Integer[] values = new Integer[size];
        for (int i = 0; i < size; i++) {
            values[i] = i;
        }

        long customTotal = 0;
        long standardTotal = 0;
        long expectedChecksum = (long) size * (size - 1) / 2;
        for (int round = 0; round < WARMUP_ROUNDS + MEASURED_ROUNDS; round++) {
            Measurement custom;
            Measurement standard;
            if (round % 2 == 0) {
                custom = measureCustom(keys, values);
                standard = measureStandard(keys, values);
            } else {
                standard = measureStandard(keys, values);
                custom = measureCustom(keys, values);
            }

            if (custom.checksum() != expectedChecksum || standard.checksum() != expectedChecksum) {
                throw new IllegalStateException("Контрольная сумма не совпала");
            }
            if (round >= WARMUP_ROUNDS) {
                customTotal += custom.nanos();
                standardTotal += standard.nanos();
            }
        }

        double divisor = MEASURED_ROUNDS * NANOS_PER_MILLISECOND;
        System.out.printf(Locale.ROOT, "| %d | %.3f | %.3f |%n",
                size, customTotal / divisor, standardTotal / divisor);
    }

    private static Integer[] generateKeys(int size) {
        Random random = new Random(SEED);
        Set<Integer> seen = new HashSet<>();
        Integer[] keys = new Integer[size];
        int index = 0;
        while (index < size) {
            int key = random.nextInt();
            if (seen.add(key)) {
                keys[index] = key;
                index++;
            }
        }
        return keys;
    }

    private static Measurement measureCustom(Integer[] keys, Integer[] values) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        long start = System.nanoTime();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        long checksum = 0;
        for (Integer key : keys) {
            checksum += map.get(key);
        }
        return new Measurement(System.nanoTime() - start, checksum);
    }

    private static Measurement measureStandard(Integer[] keys, Integer[] values) {
        HashMap<Integer, Integer> map = new HashMap<>(INITIAL_CAPACITY);
        long start = System.nanoTime();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        long checksum = 0;
        for (Integer key : keys) {
            checksum += map.get(key);
        }
        return new Measurement(System.nanoTime() - start, checksum);
    }

    private record Measurement(long nanos, long checksum) {
    }
}