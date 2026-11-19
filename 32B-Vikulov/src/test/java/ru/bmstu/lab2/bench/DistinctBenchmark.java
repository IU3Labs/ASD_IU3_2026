package ru.bmstu.lab2.bench;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;
import ru.bmstu.lab2.task_2a.HashMapCustom;
import ru.bmstu.lab2.task_3c.Distinct;

/**
 * Бенчмарк задачи 3C: {@link Distinct#distinct(List)} против {@code new LinkedHashSet<>(items)}.
 * Вход — N случайных чисел из диапазона [0, N/2), то есть около 43 % уникальных и много повторов.
 * Память — прирост занятой кучи, пока структура удерживается ссылкой (оценка через {@link Runtime}).
 * Для {@code Distinct} учитываются и внутренняя таблица {@code seen}, и список-результат: во время работы
 * существуют оба, а {@code LinkedHashSet} совмещает таблицу и порядок в одной структуре.
 */
public final class DistinctBenchmark {

    private static final int SEED = 7;
    private static final int[] SIZES = {10_000, 50_000, 100_000, 1_000_000};
    private static final double BYTES_PER_KB = 1024.0;
    /** Удерживает измеряемый объект, чтобы сборщик мусора не освободил его до второго замера. */
    private static Object sink;

    private DistinctBenchmark() {
    }

    /**
     * Печатает таблицу результатов в формате Markdown.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Bench.row("Реализация", "N элементов", "Уникальных", "Среднее время (мс)", "Пиковая память структур (КБ)");
        Bench.row("---", "---", "---", "---", "---");
        for (int size : SIZES) {
            List<Integer> items = randomItems(size);
            double customTime = Bench.averageMillis(() -> Distinct.distinct(items));
            double javaTime = Bench.averageMillis(() -> new LinkedHashSet<>(items));
            // пик памяти Distinct = внутренняя таблица seen + список-результат
            long customMemory = retainedBytes(() -> seenTable(items)) + retainedBytes(() -> Distinct.distinct(items));
            long javaMemory = retainedBytes(() -> new LinkedHashSet<>(items));
            int unique = Distinct.distinct(items).size();
            Bench.row("`Distinct.distinct`", size, unique, HashMapBenchmark.format(customTime), kb(customMemory));
            Bench.row("`new LinkedHashSet<>(items)`", size, unique, HashMapBenchmark.format(javaTime), kb(javaMemory));
        }
    }

    /** Сколько байт кучи занимает объект, созданный {@code factory}, пока на него есть ссылка. */
    private static long retainedBytes(Supplier<Object> factory) {
        long before = Bench.usedHeapBytes();
        sink = factory.get();
        long after = Bench.usedHeapBytes();
        sink = null;
        return after - before;
    }

    /** Та же таблица, что строит {@code Distinct} внутри себя для проверки «уже встречался?». */
    private static HashMapCustom<Integer, Boolean> seenTable(List<Integer> items) {
        HashMapCustom<Integer, Boolean> seen = new HashMapCustom<>();
        for (Integer item : items) {
            seen.put(item, Boolean.TRUE);
        }
        return seen;
    }

    private static String kb(long bytes) {
        return String.format(Locale.ROOT, "%.0f", bytes / BYTES_PER_KB);
    }

    private static List<Integer> randomItems(int size) {
        Random random = new Random(SEED);
        List<Integer> items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            items.add(random.nextInt(size / 2));
        }
        return items;
    }
}
