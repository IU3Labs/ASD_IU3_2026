package ru.bmstu.lab2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/** Сравнивает структуры ЛР2 со стандартными коллекциями на одинаковых данных */
public final class StructureBenchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_RUNS = 12;
    private static final int MEASURED_RUNS = 24;
    private static final int QUEUE_REMOVALS = 128;
    private static final long SEED = 2026;
    private static final double NANOS_PER_MILLISECOND = 1_000_000.0;

    private StructureBenchmark() {
    }

    /**
     * Печатает среднее время каждого сценария в формате CSV
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        System.out.println("structure,operation,n,average_ms");
        for (int size : SIZES) {
            Integer[] keys = generateKeys(size);
            measurePair(size, new String[] {"add + poll", "remove(value) x128"},
                    "PriorityQueueCustom", () -> runQueue(newCustomQueue(), keys),
                    "java.util.PriorityQueue", () -> runQueue(newStandardQueue(), keys));
            measurePair(size, new String[] {"put", "get", "remove"},
                    "HashMapCustom", () -> runMap(newCustomMap(), keys),
                    "java.util.HashMap", () -> runMap(newStandardMap(), keys));
        }
    }

    private static Integer[] generateKeys(int size) {
        Random random = new Random(SEED + size);
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

    private static void measurePair(int size, String[] operations,
            String customName, Supplier<Measurement> custom,
            String standardName, Supplier<Measurement> standard) {
        long[] customTotals = new long[operations.length];
        long[] standardTotals = new long[operations.length];
        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            boolean customFirst = run % 2 == 0;
            Measurement first = (customFirst ? custom : standard).get();
            Measurement second = (customFirst ? standard : custom).get();
            if (first.checksum() != second.checksum()) {
                throw new IllegalStateException("Результаты структур различаются");
            }
            if (run >= WARMUP_RUNS) {
                accumulate(customTotals, customFirst ? first : second);
                accumulate(standardTotals, customFirst ? second : first);
            }
        }
        for (int operation = 0; operation < operations.length; operation++) {
            printResult(customName, operations[operation], size, customTotals[operation]);
            printResult(standardName, operations[operation], size, standardTotals[operation]);
        }
    }

    private static Measurement runQueue(QueueOperations queue, Integer[] keys) {
        long started = System.nanoTime();
        fillQueue(queue, keys);
        long checksum = 0;
        for (int index = 0; index < keys.length; index++) {
            checksum = 31 * checksum + queue.poll().get();
        }
        long addAndPollTime = System.nanoTime() - started;

        fillQueue(queue, keys);
        started = System.nanoTime();
        for (int index = 0; index < QUEUE_REMOVALS; index++) {
            Integer value = keys[index * keys.length / QUEUE_REMOVALS];
            if (queue.remove().test(value)) {
                checksum = 31 * checksum + value;
            }
        }
        long removeTime = System.nanoTime() - started;
        return new Measurement(new long[] {addAndPollTime, removeTime}, checksum);
    }

    private static void fillQueue(QueueOperations queue, Integer[] keys) {
        for (Integer key : keys) {
            queue.add().accept(key);
        }
    }

    private static Measurement runMap(MapOperations map, Integer[] keys) {
        long started = System.nanoTime();
        for (Integer key : keys) {
            map.put().accept(key, key);
        }
        long putTime = System.nanoTime() - started;

        long checksum = 0;
        started = System.nanoTime();
        for (int index = keys.length - 1; index >= 0; index--) {
            checksum = 31 * checksum + map.get().apply(keys[index]);
        }
        long getTime = System.nanoTime() - started;

        started = System.nanoTime();
        for (Integer key : keys) {
            if (map.remove().test(key)) {
                checksum = 31 * checksum + key;
            }
        }
        long removeTime = System.nanoTime() - started;
        return new Measurement(new long[] {putTime, getTime, removeTime}, checksum);
    }

    private static QueueOperations newCustomQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        return new QueueOperations(queue::add, queue::poll, queue::remove);
    }

    private static QueueOperations newStandardQueue() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        return new QueueOperations(queue::add, queue::poll, queue::remove);
    }

    private static MapOperations newCustomMap() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        return new MapOperations(map::put, map::get, map::remove);
    }

    private static MapOperations newStandardMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        return new MapOperations(map::put, map::get, key -> map.remove(key) != null);
    }

    private static void accumulate(long[] totals, Measurement measurement) {
        for (int index = 0; index < totals.length; index++) {
            totals[index] += measurement.times()[index];
        }
    }

    private static void printResult(String structure, String operation, int size, long totalNanos) {
        double averageMillis = totalNanos / (MEASURED_RUNS * NANOS_PER_MILLISECOND);
        System.out.printf(Locale.ROOT, "%s,%s,%d,%.3f%n", structure, operation, size, averageMillis);
    }

    private record QueueOperations(
            Consumer<Integer> add, Supplier<Integer> poll, Predicate<Integer> remove) {
    }

    private record MapOperations(
            BiConsumer<Integer, Integer> put, Function<Integer, Integer> get, Predicate<Integer> remove) {
    }

    private record Measurement(long[] times, long checksum) {
    }
}
