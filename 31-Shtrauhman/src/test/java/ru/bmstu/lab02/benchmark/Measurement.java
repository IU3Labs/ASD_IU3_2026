package ru.bmstu.lab02.benchmark;

import java.lang.management.ManagementFactory;
import java.util.function.Supplier;

/**
 * Простой замер времени и объёма выделенной памяти для бенчмарков ЛР2.
 *
 * <p>Сначала выполняются прогревочные запуски (JIT успевает скомпилировать горячий код), затем
 * берётся среднее по измеряемым запускам. Память считается как число байт, выделенных потоком за запуск
 * ({@code com.sun.management.ThreadMXBean#getThreadAllocatedBytes}): это не зависит от момента сборки мусора,
 * в отличие от разности {@code totalMemory - freeMemory}.
 */
final class Measurement {

    private static final int WARMUP_RUNS = 10;
    private static final int MEASURED_RUNS = 20;
    private static final double NANOS_PER_MILLI = 1_000_000.0;

    private static final com.sun.management.ThreadMXBean THREADS =
            (com.sun.management.ThreadMXBean) ManagementFactory.getThreadMXBean();

    /**
     * Результат хранится в поле, чтобы JIT не выбросил вычисление как неиспользуемое.
     */
    private static volatile int sink;

    private Measurement() {
    }

    record Result(double millis, long allocatedBytes) {
    }

    static Result measure(Supplier<Object> action) {
        return measure(action, WARMUP_RUNS, MEASURED_RUNS);
    }

    static Result measure(Supplier<Object> action, int warmupRuns, int measuredRuns) {
        for (int i = 0; i < warmupRuns; i++) {
            consume(action.get());
        }
        long threadId = Thread.currentThread().threadId();
        long totalNanos = 0;
        long totalBytes = 0;
        for (int i = 0; i < measuredRuns; i++) {
            long bytesBefore = THREADS.getThreadAllocatedBytes(threadId);
            long start = System.nanoTime();
            Object result = action.get();
            totalNanos += System.nanoTime() - start;
            totalBytes += THREADS.getThreadAllocatedBytes(threadId) - bytesBefore;
            consume(result);
        }
        return new Result(totalNanos / NANOS_PER_MILLI / measuredRuns, totalBytes / measuredRuns);
    }

    private static void consume(Object result) {
        sink += System.identityHashCode(result);
    }
}
