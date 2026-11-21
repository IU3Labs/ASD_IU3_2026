package ru.bmstu.lab2.benchmark;

import com.sun.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.util.Locale;
import java.util.function.LongSupplier;

/** Прогрев, измерения времени и выделенных байтов в текущем потоке. */
final class BenchmarkSupport {
    static final int WARMUPS = 8;
    static final int REPETITIONS = 12;
    private static final double NANOS_PER_MILLISECOND = 1_000_000.0;
    private static final double BYTES_PER_MEBIBYTE = 1_048_576.0;
    private static volatile long sink;
    private final ThreadMXBean bean;

    BenchmarkSupport() {
        bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        if (!bean.isThreadAllocatedMemorySupported()) {
            throw new IllegalStateException("JVM не поддерживает счётчик выделения памяти");
        }
        bean.setThreadAllocatedMemoryEnabled(true);
    }

    Measurement measure(LongSupplier operation) {
        for (int run = 0; run < WARMUPS; run++) {
            sink = operation.getAsLong();
        }
        double sum = 0;
        double sumSquares = 0;
        long bytes = 0;
        for (int run = 0; run < REPETITIONS; run++) {
            long before = bean.getCurrentThreadAllocatedBytes();
            long start = System.nanoTime();
            long value = operation.getAsLong();
            long elapsed = System.nanoTime() - start;
            bytes += bean.getCurrentThreadAllocatedBytes() - before;
            sink = value;
            double milliseconds = elapsed / NANOS_PER_MILLISECOND;
            sum += milliseconds;
            sumSquares += milliseconds * milliseconds;
        }
        double mean = sum / REPETITIONS;
        double deviation = Math.sqrt(Math.max(0, sumSquares / REPETITIONS - mean * mean));
        return new Measurement(mean, deviation, bytes / (double) REPETITIONS / BYTES_PER_MEBIBYTE);
    }

    record Measurement(double milliseconds, double deviation, double allocatedMebibytes) {
        String cells() {
            return String.format(Locale.ROOT, "%.3f | %.3f | %.3f",
                    milliseconds, deviation, allocatedMebibytes);
        }
    }
}
