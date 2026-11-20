package ru.bmstu.lab2.benchmark;

import java.util.Locale;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/** Учебный измеритель: отдельная подготовка, прогрев, повторы и проверка результата. */
final class BenchmarkTimer {
    private static final int WARMUP_RUNS = 3;
    private static final int MEASURED_RUNS = 7;
    private static final int COLLISION_WARMUPS = 1;
    private static final int COLLISION_RUNS = 3;
    private static final double NANOS_PER_MILLISECOND = 1_000_000.0;

    private BenchmarkTimer() {}

    static void measure(
            String group,
            String operation,
            String implementation,
            int size,
            Supplier<LongSupplier> setup,
            long expected) {
        boolean collisions = group.startsWith("collision");
        int warmups = collisions ? COLLISION_WARMUPS : WARMUP_RUNS;
        int repeats = collisions ? COLLISION_RUNS : MEASURED_RUNS;
        long sum = 0;
        long minimum = Long.MAX_VALUE;
        long maximum = 0;
        for (int run = -warmups; run < repeats; run++) {
            LongSupplier action = setup.get();
            long start = System.nanoTime();
            long actual = action.getAsLong();
            long elapsed = System.nanoTime() - start;
            if (actual != expected) {
                throw new AssertionError(implementation + ": неверная контрольная сумма " + actual);
            }
            if (run >= 0) {
                sum += elapsed;
                minimum = Math.min(minimum, elapsed);
                maximum = Math.max(maximum, elapsed);
            }
        }
        printResult(group, operation, implementation, size, sum, minimum, maximum, repeats);
    }

    private static void printResult(
            String group,
            String operation,
            String implementation,
            int size,
            long sum,
            long minimum,
            long maximum,
            int repeats) {
        System.out.printf(
                Locale.ROOT,
                "%s,%s,%s,%d,%.6f,%.6f,%.6f,%d%n",
                group,
                operation,
                implementation,
                size,
                sum / (repeats * NANOS_PER_MILLISECOND),
                minimum / NANOS_PER_MILLISECOND,
                maximum / NANOS_PER_MILLISECOND,
                repeats);
    }
}
