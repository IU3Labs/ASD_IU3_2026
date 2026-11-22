package ru.bmstu.lab2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

/** Локальные замеры структур данных на одном и том же наборе значений. */
public final class BenchmarksMain {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int REPETITIONS = 5;
    private static volatile long sink;

    private BenchmarksMain() {
    }

    /** Запускает несколько прогревочных и измеряемых повторений. */
    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N | Среднее время, мс |");
        System.out.println("|---|---|---:|---:|");
        for (int count : SIZES) {
            int[] input = randomNumbers(count);
            print("PriorityQueueCustom", "add + poll", count, () -> runCustom(input, null));
            print("java.util.PriorityQueue", "add + poll", count, () -> runStandard(input));
            print("HashMapCustom", "put + get", count, () -> runCustomMap(input));
            print("java.util.HashMap", "put + get", count, () -> runStandardMap(input));
        }
        int[] input = randomNumbers(100_000);
        print("PriorityQueueCustom min", "add + poll", input.length, () -> runCustom(input, null));
        print("PriorityQueueCustom max", "add + poll", input.length,
                () -> runCustom(input, Comparator.reverseOrder()));
        Integer[] boxed = new Integer[input.length];
        for (int index = 0; index < input.length; index++) {
            boxed[index] = input[index];
        }
        print("PriorityQueueCustom", "построение add", input.length, () -> buildByAdding(boxed));
        print("PriorityQueueCustom", "построение heapify", input.length, () -> buildByHeapify(boxed));
        System.err.println("Контрольная сумма измерений: " + sink);
    }

    private static int[] randomNumbers(int count) {
        Random random = new Random(31);
        int[] result = new int[count];
        for (int index = 0; index < count; index++) {
            result[index] = random.nextInt();
        }
        return result;
    }

    private static void runCustom(int[] input, Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        for (int value : input) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        sink = checksum;
    }

    private static void buildByAdding(Integer[] input) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : input) {
            queue.add(value);
        }
        sink = queue.size();
    }

    private static void buildByHeapify(Integer[] input) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input);
        sink = queue.size();
    }

    private static void runCustomMap(int[] input) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int value : input) {
            map.put(value, value);
        }
        long checksum = 0;
        for (int value : input) {
            Integer result = map.get(value);
            checksum += result == null ? 0 : result;
        }
        sink = checksum;
    }

    private static void runStandardMap(int[] input) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int value : input) {
            map.put(value, value);
        }
        long checksum = 0;
        for (int value : input) {
            Integer result = map.get(value);
            checksum += result == null ? 0 : result;
        }
        sink = checksum;
    }

    private static void runStandard(int[] input) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int value : input) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        sink = checksum;
    }

    private static void print(String structure, String operation, int size, Runnable action) {
        for (int index = 0; index < 2; index++) {
            action.run();
        }
        long totalNanos = 0;
        for (int index = 0; index < REPETITIONS; index++) {
            long start = System.nanoTime();
            action.run();
            totalNanos += System.nanoTime() - start;
        }
        double meanMillis = totalNanos / (REPETITIONS * 1_000_000.0);
        System.out.printf(java.util.Locale.US, "| %s | %s | %d | %.3f |%n",
                structure, operation, size, meanMillis);
    }
}
