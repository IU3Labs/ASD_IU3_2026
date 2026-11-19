package ru.bmstu.lab02;

import ru.bmstu.lab02.task_01a.PriorityQueueCustom;
import ru.bmstu.lab02.task_01b.ComparatorPriorityQueue;
import ru.bmstu.lab02.task_01c.RemovablePriorityQueue;
import ru.bmstu.lab02.task_02a.HashMapCustom;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;

public class Lab02Benchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int MIN_MAX_SIZE = 100_000;
    private static final int REMOVE_COUNT = 1_000;
    private static final int WARMUP_RUNS = 5;
    private static final int RUNS = 20;
    private static final long SEED = 25;

    private static long checksum = 0;

    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) |");
        System.out.println("|---|---|---|---|");
        for (int n : SIZES) {
            Integer[] data = randomData(n);
            printRow("PriorityQueueCustom", "add + poll (цикл)", n, average(() -> customQueueCycle(data)));
            printRow("java.util.PriorityQueue", "add + poll (цикл)", n, average(() -> javaQueueCycle(data)));
            printRow("RemovablePriorityQueue", "remove 1000 эл.", n, average(() -> customQueueRemove(data)));
            printRow("java.util.PriorityQueue", "remove 1000 эл.", n, average(() -> javaQueueRemove(data)));
            printRow("HashMapCustom", "put", n, average(() -> customMapPut(data)));
            printRow("java.util.HashMap", "put", n, average(() -> javaMapPut(data)));
            printRow("HashMapCustom", "get", n, average(() -> customMapGet(data)));
            printRow("java.util.HashMap", "get", n, average(() -> javaMapGet(data)));
            printRow("HashMapCustom", "remove", n, average(() -> customMapRemove(data)));
            printRow("java.util.HashMap", "remove", n, average(() -> javaMapRemove(data)));
        }
        compareMinAndMax();
        printCapacities();
        System.out.println("checksum = " + checksum);
    }

    private static void compareMinAndMax() {
        Integer[] data = randomData(MIN_MAX_SIZE);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();
        System.out.println();
        System.out.println("| Вариант | N | Среднее время add + poll (мс) | Число сравнений |");
        System.out.println("|---|---|---|---|");
        printMinMaxRow("min-heap (без компаратора, null)", null, data);
        printMinMaxRow("min-heap (naturalOrder)", natural, data);
        printMinMaxRow("max-heap (reverseOrder)", reverse, data);
    }

    private static void printMinMaxRow(String name, Comparator<Integer> comparator, Integer[] data) {
        double time = average(() -> queueCycleWithComparator(data, comparator));
        long comparisons = countComparisons(data, comparator);
        System.out.printf("| %s | %d | %.3f | %d |%n", name, data.length, time, comparisons);
    }

    private static void printCapacities() {
        System.out.println();
        System.out.println("| N | Ёмкость HashMapCustom | Коэффициент загрузки |");
        System.out.println("|---|---|---|");
        for (int n : SIZES) {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
            for (Integer value : randomData(n)) {
                map.put(value, value);
            }
            double load = (double) map.size() / map.capacity();
            System.out.printf("| %d | %d | %.3f |%n", n, map.capacity(), load);
        }
    }

    private static long customQueueCycle(Integer[] data) {
        long start = System.nanoTime();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return System.nanoTime() - start;
    }

    private static long javaQueueCycle(Integer[] data) {
        long start = System.nanoTime();
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return System.nanoTime() - start;
    }

    private static long queueCycleWithComparator(Integer[] data, Comparator<Integer> comparator) {
        long start = System.nanoTime();
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>(comparator);
        for (Integer value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return System.nanoTime() - start;
    }

    private static long countComparisons(Integer[] data, Comparator<Integer> comparator) {
        long[] counter = {0};
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>((a, b) -> {
            counter[0]++;
            if (comparator == null) {
                return a.compareTo(b);
            }
            return comparator.compare(a, b);
        });
        for (Integer value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            queue.poll();
        }
        return counter[0];
    }

    private static long customQueueRemove(Integer[] data) {
        RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();
        for (Integer value : data) {
            queue.add(value);
        }
        int step = data.length / REMOVE_COUNT;
        long start = System.nanoTime();
        for (int i = 0; i < REMOVE_COUNT; i++) {
            if (queue.remove(data[i * step])) {
                checksum++;
            }
        }
        return System.nanoTime() - start;
    }

    private static long javaQueueRemove(Integer[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer value : data) {
            queue.add(value);
        }
        int step = data.length / REMOVE_COUNT;
        long start = System.nanoTime();
        for (int i = 0; i < REMOVE_COUNT; i++) {
            if (queue.remove(data[i * step])) {
                checksum++;
            }
        }
        return System.nanoTime() - start;
    }

    private static long customMapPut(Integer[] data) {
        long start = System.nanoTime();
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (Integer value : data) {
            map.put(value, value);
        }
        checksum += map.size();
        return System.nanoTime() - start;
    }

    private static long javaMapPut(Integer[] data) {
        long start = System.nanoTime();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer value : data) {
            map.put(value, value);
        }
        checksum += map.size();
        return System.nanoTime() - start;
    }

    private static long customMapGet(Integer[] data) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (Integer value : data) {
            map.put(value, value);
        }
        long start = System.nanoTime();
        for (Integer value : data) {
            checksum += map.get(value);
        }
        return System.nanoTime() - start;
    }

    private static long javaMapGet(Integer[] data) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer value : data) {
            map.put(value, value);
        }
        long start = System.nanoTime();
        for (Integer value : data) {
            checksum += map.get(value);
        }
        return System.nanoTime() - start;
    }

    private static long customMapRemove(Integer[] data) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (Integer value : data) {
            map.put(value, value);
        }
        long start = System.nanoTime();
        for (Integer value : data) {
            if (map.remove(value)) {
                checksum++;
            }
        }
        return System.nanoTime() - start;
    }

    private static long javaMapRemove(Integer[] data) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer value : data) {
            map.put(value, value);
        }
        long start = System.nanoTime();
        for (Integer value : data) {
            if (map.remove(value) != null) {
                checksum++;
            }
        }
        return System.nanoTime() - start;
    }

    private static double average(LongSupplier experiment) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            experiment.getAsLong();
        }
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            total += experiment.getAsLong();
        }
        return total / (double) RUNS / 1_000_000;
    }

    private static Integer[] randomData(int n) {
        Random random = new Random(SEED);
        Integer[] data = new Integer[n];
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    private static void printRow(String structure, String operation, int n, double time) {
        System.out.printf("| `%s` | %s | %d | %.3f |%n", structure, operation, n, time);
    }
}
