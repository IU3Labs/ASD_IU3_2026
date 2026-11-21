package ru.bmstu.lab02.task_1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Сравнивает очереди и способы построения кучи на одинаковых данных
 */
public final class PriorityQueueBenchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_ROUNDS = 5;
    private static final int MEASURED_ROUNDS = 11;
    private static final long SEED = 33;
    private static final Comparator<Integer> MIN_ORDER = Comparator.naturalOrder();
    private static final Comparator<Integer> MAX_ORDER = Comparator.reverseOrder();
    private static volatile long checksum;
    private static volatile Object builtQueue;

    private PriorityQueueBenchmark() {
    }

    /**
     * Печатает средние времени в виде строк для таблицы Markdown
     * @param args - аргументы запуска, не используются
     */
    public static void main(String[] args) {
        Integer[] allData = randomData(SIZES[SIZES.length - 1]);
        System.out.println("add + poll:");
        for (int size : SIZES) {
            Integer[] data = Arrays.copyOf(allData, size);
            printPair(size, () -> customCycle(data, MIN_ORDER), () -> standardCycle(data));
        }
        System.out.println("min-heap / max-heap:");
        printPair(allData.length, () -> customCycle(allData, MIN_ORDER), () -> customCycle(allData, MAX_ORDER));
        System.out.println("add / heapify:");
        printPair(allData.length, () -> builtQueue = buildByAdding(allData),
                () -> builtQueue = new PriorityQueueCustom<>(allData));
    }

    private static Integer[] randomData(int size) {
        Random random = new Random(SEED);
        Integer[] data = new Integer[size];
        for (int index = 0; index < size; index++) {
            data[index] = random.nextInt();
        }
        return data;
    }

    private static void customCycle(Integer[] data, Comparator<Integer> order) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(order);
        for (Integer value : data) {
            queue.add(value);
        }
        long result = 0;
        while (!queue.isEmpty()) {
            result = 31 * result + queue.poll();
        }
        checksum = result;
    }

    private static void standardCycle(Integer[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(MIN_ORDER);
        for (Integer value : data) {
            queue.add(value);
        }
        long result = 0;
        while (!queue.isEmpty()) {
            result = 31 * result + queue.poll();
        }
        checksum = result;
    }

    private static PriorityQueueCustom<Integer> buildByAdding(Integer[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : data) {
            queue.add(value);
        }
        return queue;
    }

    private static void printPair(int size, Runnable first, Runnable second) {
        long[] firstTimes = new long[MEASURED_ROUNDS];
        long[] secondTimes = new long[MEASURED_ROUNDS];
        for (int round = -WARMUP_ROUNDS; round < MEASURED_ROUNDS; round++) {
            boolean firstStarts = (round & 1) == 0;
            long startTime = measure(firstStarts ? first : second);
            long endTime = measure(firstStarts ? second : first);
            if (round >= 0) {
                firstTimes[round] = firstStarts ? startTime : endTime;
                secondTimes[round] = firstStarts ? endTime : startTime;
            }
        }
        System.out.printf(Locale.ROOT, "| %,d | %.3f | %.3f |%n",
                size, meanMillis(firstTimes), meanMillis(secondTimes));
    }

    private static long measure(Runnable operation) {
        long start = System.nanoTime();
        operation.run();
        return System.nanoTime() - start;
    }

    private static double meanMillis(long[] samples) {
        return Arrays.stream(samples).average().orElseThrow() / 1_000_000.0;
    }
}
