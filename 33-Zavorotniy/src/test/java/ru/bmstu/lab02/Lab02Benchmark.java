package ru.bmstu.lab02;

import com.sun.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/** Сравнивает структуры со стандартными аналогами на одинаковых входных данных. */
public final class Lab02Benchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final long SEED = 33;
    private static final int WARMUP_RUNS = 12;
    private static final int MEASURED_RUNS = 20;
    private static final int NANOS_PER_MILLISECOND = 1_000_000;
    private static final int BYTES_PER_MEBIBYTE = 1 << 20;

    private Lab02Benchmark() {}

    /** Печатает таблицу замеров в Markdown. Запускается в отдельной JVM без агента покрытия. */
    public static void main(String[] args) {
        ThreadMXBean memory = ManagementFactory.getPlatformMXBean(ThreadMXBean.class);
        if (memory == null || !memory.isThreadAllocatedMemorySupported()) {
            throw new IllegalStateException("The JVM must support thread allocation counters");
        }
        memory.setThreadAllocatedMemoryEnabled(true);

        System.out.println(
                "| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---:|---:|---|");
        for (int size : SIZES) {
            Integer[] input = randomInput(size);
            compareQueues(memory, input);
            compareMaps(memory, input);
        }
    }

    private static void compareQueues(ThreadMXBean memory, Integer[] input) {
        compare(
                memory,
                input,
                "add + poll",
                new String[] {"PriorityQueueCustom", "java.util.PriorityQueue"},
                () -> {
                    PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
                    return runQueue(input, queue::add, queue::poll);
                },
                () -> {
                    PriorityQueue<Integer> queue = new PriorityQueue<>();
                    return runQueue(input, queue::add, queue::poll);
                });
    }

    private static void compareMaps(ThreadMXBean memory, Integer[] input) {
        compare(
                memory,
                input,
                "put + get + remove",
                new String[] {"HashMapCustom", "java.util.HashMap"},
                () -> {
                    HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
                    return runMap(input, map::put, map::get, map::remove, map::size);
                },
                () -> {
                    HashMap<Integer, Integer> map = new HashMap<>();
                    return runMap(input, map::put, map::get, map::remove, map::size);
                });
    }

    private static long runQueue(Integer[] input, Consumer<Integer> add, Supplier<Integer> poll) {
        for (Integer item : input) {
            add.accept(item);
        }
        long sum = 0;
        for (int index = 0; index < input.length; index++) {
            sum += poll.get();
        }
        return sum;
    }

    private static long runMap(
            Integer[] input,
            BiConsumer<Integer, Integer> put,
            Function<Integer, Integer> get,
            Consumer<Integer> remove,
            IntSupplier size) {
        for (Integer key : input) {
            put.accept(key, key);
        }
        long sum = 0;
        for (Integer key : input) {
            sum += get.apply(key);
        }
        for (Integer key : input) {
            remove.accept(key);
        }
        if (size.getAsInt() != 0) {
            throw new IllegalStateException("The map is not empty after removals");
        }
        return sum;
    }

    private static void compare(
            ThreadMXBean memory,
            Integer[] input,
            String operation,
            String[] names,
            LongSupplier... workloads) {
        long expected = 0;
        for (Integer item : input) {
            expected += item;
        }
        long[] nanos = new long[workloads.length];
        long[] bytes = new long[workloads.length];

        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            for (int offset = 0; offset < workloads.length; offset++) {
                int index = (run + offset) % workloads.length;
                long initialBytes = memory.getCurrentThreadAllocatedBytes();
                long start = System.nanoTime();
                long actual = workloads[index].getAsLong();
                long elapsed = System.nanoTime() - start;
                long allocated = memory.getCurrentThreadAllocatedBytes() - initialBytes;
                if (actual != expected) {
                    throw new IllegalStateException("Unexpected checksum for " + names[index]);
                }
                if (run >= WARMUP_RUNS) {
                    nanos[index] += elapsed;
                    bytes[index] += allocated;
                }
            }
        }
        printAverages(input.length, operation, names, nanos, bytes);
    }

    private static void printAverages(
            int size, String operation, String[] names, long[] nanos, long[] bytes) {
        for (int index = 0; index < names.length; index++) {
            double millis = (double) nanos[index] / MEASURED_RUNS / NANOS_PER_MILLISECOND;
            double mebibytes = (double) bytes[index] / MEASURED_RUNS / BYTES_PER_MEBIBYTE;
            System.out.printf(
                    Locale.ROOT,
                    "| `%s` | %s | %d | %.3f | Выделено %.3f МиБ |%n",
                    names[index],
                    operation,
                    size,
                    millis,
                    mebibytes);
        }
    }

    private static Integer[] randomInput(int size) {
        Random random = new Random(SEED);
        HashSet<Integer> seen = new HashSet<>();
        Integer[] input = new Integer[size];
        int index = 0;
        while (index < size) {
            Integer item = random.nextInt();
            if (seen.add(item)) {
                input[index++] = item;
            }
        }
        return input;
    }
}
