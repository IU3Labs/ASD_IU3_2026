package ru.bmstu.lab02.task_1;

import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

/** Сравнивает время добавления и извлечения элементов двух очередей. */
public final class PriorityQueueBenchmark {

    private static final int WARMUP_ROUNDS = 3;
    private static final int MEASURED_ROUNDS = 8;
    private static final int INITIAL_CAPACITY = 8;
    private static final long SEED = 42L;

    private PriorityQueueBenchmark() {
    }

    /** Запускает замеры для трёх размеров входных данных. */
    public static void main(String[] args) {
        System.out.println("| Элементов | PriorityQueueCustom, мс | PriorityQueue, мс |");
        System.out.println("|---:|---:|---:|");
        for (int size : new int[] {10_000, 50_000, 100_000}) {
            compare(size);
        }
    }

    private static void compare(int size) {
        Random random = new Random(SEED);
        Integer[] values = new Integer[size];
        for (int i = 0; i < size; i++) {
            values[i] = random.nextInt();
        }

        long customTotal = 0;
        long standardTotal = 0;
        for (int round = 0; round < WARMUP_ROUNDS + MEASURED_ROUNDS; round++) {
            Measurement custom;
            Measurement standard;
            if (round % 2 == 0) {
                custom = measureCustom(values);
                standard = measureStandard(values);
            } else {
                standard = measureStandard(values);
                custom = measureCustom(values);
            }

            if (custom.checksum() != standard.checksum()) {
                throw new IllegalStateException("Контрольные суммы не совпали");
            }
            if (round >= WARMUP_ROUNDS) {
                customTotal += custom.nanos();
                standardTotal += standard.nanos();
            }
        }

        double nanosPerMillisecond = 1_000_000.0;
        double divisor = MEASURED_ROUNDS * nanosPerMillisecond;
        System.out.printf(Locale.ROOT, "| %d | %.3f | %.3f |%n",
                size, customTotal / divisor, standardTotal / divisor);
    }

    private static Measurement measureCustom(Integer[] values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        long start = System.nanoTime();
        for (Integer value : values) {
            queue.add(value);
        }
        long checksum = 0;
        for (int i = 0; i < values.length; i++) {
            checksum += queue.poll();
        }
        return new Measurement(System.nanoTime() - start, checksum);
    }

    private static Measurement measureStandard(Integer[] values) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(INITIAL_CAPACITY);
        long start = System.nanoTime();
        for (Integer value : values) {
            queue.add(value);
        }
        long checksum = 0;
        for (int i = 0; i < values.length; i++) {
            checksum += queue.poll();
        }
        return new Measurement(System.nanoTime() - start, checksum);
    }

    private record Measurement(long nanos, long checksum) {
    }
}