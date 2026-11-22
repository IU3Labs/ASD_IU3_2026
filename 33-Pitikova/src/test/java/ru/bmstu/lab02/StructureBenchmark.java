package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;

/** Снимает замеры для сравнения структур и двух способов построения кучи. */
public final class StructureBenchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_ROUNDS = 5;
    private static final int MEASUREMENT_ROUNDS = 15;
    private static final long SEED = 42L;

    private static volatile long checksum;

    private StructureBenchmark() {
    }

    /** Печатает в stdout таблицы средних времён в формате Markdown. */
    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---:|---:|---|");
        for (int size : SIZES) {
            Integer[] values = randomUniqueValues(size);
            printPair(
                    "PriorityQueueCustom", "java.util.PriorityQueue", "add + poll", size,
                    () -> runCustomQueue(values), () -> runStandardQueue(values), "N вставок, затем N извлечений");
            printPair(
                    "HashMapCustom", "java.util.HashMap", "put + get + remove", size,
                    () -> runCustomMap(values), () -> runStandardMap(values),
                    "N вставок, N успешных поисков, N удалений");
        }

        System.out.println();
        System.out.println("| Способ построения PriorityQueueCustom | N элементов | Среднее время (мс) |");
        System.out.println("|---|---:|---:|");
        Integer[] values = randomUniqueValues(SIZES[SIZES.length - 1]);
        double[] time = compare(
                () -> buildWithAdds(values),
                () -> observeQueue(new PriorityQueueCustom<>(values)),
                values.length + (long) Arrays.stream(values).mapToInt(Integer::intValue).min().orElseThrow());
        printBuildRow("Последовательные add", values.length, time[0]);
        printBuildRow("Построение снизу вверх", values.length, time[1]);
        printHeapModes(values);
    }

    private static void printHeapModes(Integer[] values) {
        LongSupplier min = () -> runCustomQueue(values, Comparator.naturalOrder(), false);
        LongSupplier max = () -> runCustomQueue(values, Comparator.reverseOrder(), true);
        Integer[] sorted = values.clone();
        Arrays.sort(sorted);
        long expected = 0;
        for (int index = 0; index < sorted.length; index++) {
            expected += (long) (index + 1) * sorted[index];
        }
        double[] time = compare(min, max, expected);
        System.out.println();
        System.out.println("| Режим очереди (add + poll) | N элементов | Среднее время (мс) |");
        System.out.println("|---|---:|---:|");
        printBuildRow("Min-heap", values.length, time[0]);
        printBuildRow("Max-heap", values.length, time[1]);
    }

    private static void printBuildRow(String method, int size, double time) {
        System.out.printf(Locale.ROOT, "| %s | %d | %.3f |%n", method, size, time);
    }

    private static Integer[] randomUniqueValues(int size) {
        var values = new LinkedHashSet<Integer>();
        Random random = new Random(SEED);
        while (values.size() < size) {
            values.add(random.nextInt());
        }
        return values.toArray(Integer[]::new);
    }

    private static void printPair(
            String customName, String standardName, String operation, int size,
            LongSupplier custom, LongSupplier standard, String note) {
        double[] time = compare(custom, standard, standard.getAsLong());
        printRow(customName, operation, size, time[0], note);
        printRow(standardName, operation, size, time[1], note);
    }

    private static void printRow(String name, String operation, int size, double time, String note) {
        System.out.printf(Locale.ROOT, "| `%s` | %s | %d | %.3f | %s |%n", name, operation, size, time, note);
    }

    private static double[] compare(LongSupplier first, LongSupplier second, long expected) {
        long[] elapsed = new long[2];
        LongSupplier[] actions = {first, second};
        for (int round = 0; round < WARMUP_ROUNDS + MEASUREMENT_ROUNDS; round++) {
            // Меняем порядок запусков, чтобы обе структуры иногда измерялись первыми.
            for (int position = 0; position < actions.length; position++) {
                int index = (round + position) % actions.length;
                long nanos = measure(actions[index], expected);
                if (round >= WARMUP_ROUNDS) {
                    elapsed[index] += nanos;
                }
            }
        }
        return new double[] {
            elapsed[0] / 1_000_000.0 / MEASUREMENT_ROUNDS,
            elapsed[1] / 1_000_000.0 / MEASUREMENT_ROUNDS
        };
    }

    private static long measure(LongSupplier action, long expected) {
        long start = System.nanoTime();
        long result = action.getAsLong();
        long elapsed = System.nanoTime() - start;
        if (result != expected) {
            throw new IllegalStateException("Контрольный результат бенчмарка не совпал");
        }
        checksum = result;
        return elapsed;
    }

    private static long runCustomQueue(Integer[] values) {
        return runCustomQueue(values, null, false);
    }

    private static long runCustomQueue(Integer[] values, Comparator<Integer> comparator, boolean reversed) {
        var queue = new PriorityQueueCustom<>(comparator);
        for (Integer value : values) {
            queue.add(value);
        }
        long result = 0;
        for (int index = 0; index < values.length; index++) {
            int weight = reversed ? values.length - index : index + 1;
            result += (long) weight * queue.poll();
        }
        return result;
    }

    private static long runStandardQueue(Integer[] values) {
        var queue = new PriorityQueue<Integer>();
        for (Integer value : values) {
            queue.add(value);
        }
        long result = 0;
        for (int index = 0; index < values.length; index++) {
            result += (long) (index + 1) * queue.remove();
        }
        return result;
    }

    private static long runCustomMap(Integer[] values) {
        var map = new HashMapCustom<Integer, Integer>();
        for (Integer value : values) {
            map.put(value, value);
        }
        long result = 0;
        for (Integer value : values) {
            result += map.get(value);
        }
        int removed = 0;
        for (Integer value : values) {
            if (map.remove(value)) {
                removed++;
            }
        }
        return result + removed - map.size();
    }

    private static long runStandardMap(Integer[] values) {
        var map = new HashMap<Integer, Integer>();
        for (Integer value : values) {
            map.put(value, value);
        }
        long result = 0;
        for (Integer value : values) {
            result += map.get(value);
        }
        int removed = 0;
        for (Integer value : values) {
            if (map.remove(value) != null) {
                removed++;
            }
        }
        return result + removed - map.size();
    }

    private static long buildWithAdds(Integer[] values) {
        var queue = new PriorityQueueCustom<Integer>();
        for (Integer value : values) {
            queue.add(value);
        }
        return observeQueue(queue);
    }

    private static long observeQueue(PriorityQueueCustom<Integer> queue) {
        return (long) queue.size() + queue.peek();
    }
}
