package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.function.ToLongFunction;

/** Воспроизводит замеры структур на одинаковых наборах случайных целых чисел. */
public final class StructuresBenchmark {
    private static final int RANDOM_SEED = 42;
    private static final int WARMUP_ROUNDS = 10;
    private static final int MEASUREMENT_ROUNDS = 20;
    private static final long NANOS_PER_MILLISECOND = 1_000_000;
    private static volatile long checksum;

    private StructuresBenchmark() {
    }

    /** Выводит среднее время каждого сценария в миллисекундах. */
    public static void main(String[] args) {
        List<Scenario> scenarios = List.of(
                new Scenario("PriorityQueueCustom (min)", "add + poll", values -> runCustomQueue(values, null)),
                new Scenario("PriorityQueueCustom (max)", "add + poll",
                        values -> runCustomQueue(values, Comparator.reverseOrder())),
                new Scenario("java.util.PriorityQueue", "add + poll", StructuresBenchmark::runStandardQueue),
                new Scenario("HashMapCustom", "put + get + remove", StructuresBenchmark::runCustomMap),
                new Scenario("java.util.HashMap", "put + get + remove", StructuresBenchmark::runStandardMap));

        System.out.println("| Структура | Операция | N | Среднее время, мс |");
        System.out.println("| --- | --- | ---: | ---: |");
        for (int size : new int[] { 10_000, 50_000, 100_000 }) {
            measure(scenarios, randomValues(size));
        }
    }

    private static void measure(List<Scenario> scenarios, Integer[] values) {
        long[] elapsed = new long[scenarios.size()];
        for (int round = 0; round < WARMUP_ROUNDS + MEASUREMENT_ROUNDS; round++) {
            // Меняем первый сценарий, чтобы каждый вариант занимал разные позиции в серии.
            for (int offset = 0; offset < scenarios.size(); offset++) {
                int index = (round + offset) % scenarios.size();
                Scenario scenario = scenarios.get(index);
                long start = System.nanoTime();
                checksum = scenario.run().applyAsLong(values);
                if (round >= WARMUP_ROUNDS) {
                    elapsed[index] += System.nanoTime() - start;
                }
            }
        }
        for (int index = 0; index < scenarios.size(); index++) {
            Scenario scenario = scenarios.get(index);
            double average = (double) elapsed[index] / MEASUREMENT_ROUNDS / NANOS_PER_MILLISECOND;
            System.out.printf(Locale.ROOT, "| `%s` | %s | %d | %.3f |%n",
                    scenario.name(), scenario.operation(), values.length, average);
        }
    }

    private static Integer[] randomValues(int size) {
        Random random = new Random(RANDOM_SEED);
        Set<Integer> seen = new HashSet<>();
        Integer[] values = new Integer[size];
        for (int index = 0; index < size;) {
            Integer value = random.nextInt();
            if (seen.add(value)) {
                values[index++] = value;
            }
        }
        return values;
    }

    private static long runCustomQueue(Integer[] values, Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        for (Integer value : values) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long runStandardQueue(Integer[] values) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer value : values) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long runCustomMap(Integer[] values) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (Integer value : values) {
            map.put(value, value);
        }
        long sum = 0;
        for (Integer value : values) {
            sum += map.get(value);
        }
        for (Integer value : values) {
            sum += map.remove(value) ? 1 : 0;
        }
        return sum;
    }

    private static long runStandardMap(Integer[] values) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer value : values) {
            map.put(value, value);
        }
        long sum = 0;
        for (Integer value : values) {
            sum += map.get(value);
        }
        for (Integer value : values) {
            sum += map.remove(value) != null ? 1 : 0;
        }
        return sum;
    }

    private record Scenario(String name, String operation, ToLongFunction<Integer[]> run) {
    }
}
