package ru.bmstu.lab2.bench;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Простейший замер времени: прогрев, затем среднее по нескольким запускам.
 *
 * <p>Прогрев нужен, чтобы JIT-компилятор успел скомпилировать горячий код: первые запуски в JVM
 * идут в интерпретаторе и сильно медленнее. Каждая программа-бенчмарк запускается в отдельной JVM.
 */
final class Bench {

    static final int[] SIZES = {10_000, 50_000, 100_000};
    /** Прогрев длится не меньше этого времени: на малых N десяти запусков не хватает, чтобы JIT успел. */
    private static final long WARMUP_NANOS = 500_000_000L;
    private static final int MEASURED_RUNS = 20;
    private static final double NANOS_PER_MILLI = 1_000_000.0;

    private Bench() {
    }

    /** Среднее время одного запуска {@code action} в миллисекундах. */
    static double averageMillis(Runnable action) {
        long warmupEnd = System.nanoTime() + WARMUP_NANOS;
        while (System.nanoTime() < warmupEnd) {
            action.run();
        }
        long total = 0;
        for (int i = 0; i < MEASURED_RUNS; i++) {
            long start = System.nanoTime();
            action.run();
            total += System.nanoTime() - start;
        }
        return total / NANOS_PER_MILLI / MEASURED_RUNS;
    }

    /**
     * Среднее время {@code action} в миллисекундах, когда каждому запуску нужно свежее состояние
     * (например, заполненная таблица для замера удалений). {@code setup} выполняется вне замера.
     */
    static <T> double averageMillis(Supplier<T> setup, Consumer<T> action) {
        long warmupEnd = System.nanoTime() + WARMUP_NANOS;
        while (System.nanoTime() < warmupEnd) {
            action.accept(setup.get());
        }
        long total = 0;
        for (int i = 0; i < MEASURED_RUNS; i++) {
            T state = setup.get();
            long start = System.nanoTime();
            action.accept(state);
            total += System.nanoTime() - start;
        }
        return total / NANOS_PER_MILLI / MEASURED_RUNS;
    }

    /** Занятая память кучи в байтах после принудительной сборки мусора (оценка, точность ~±1 МБ). */
    static long usedHeapBytes() {
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < 3; i++) {
            System.gc();
        }
        return runtime.totalMemory() - runtime.freeMemory();
    }

    /** Строка таблицы Markdown. */
    static void row(Object... cells) {
        StringBuilder line = new StringBuilder("|");
        for (Object cell : cells) {
            line.append(' ').append(cell).append(" |");
        }
        System.out.println(line);
    }
}
