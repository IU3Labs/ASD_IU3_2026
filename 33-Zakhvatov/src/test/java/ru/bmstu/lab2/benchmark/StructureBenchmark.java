package ru.bmstu.lab2.benchmark;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import ru.bmstu.lab2.task_1a_1b_1d.PriorityQueueCustom;
import ru.bmstu.lab2.task_2a_2b.HashMapCustom;

/** Замеры очереди и двух вариантов таблицы. */
final class StructureBenchmark {
    String run() {
        StringBuilder table = new StringBuilder();
        table.append("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |\n");
        table.append("|---|---|---:|---:|---|\n");
        for (int n : new int[]{10_000, 50_000, 100_000}) {
            Integer[] input = randomArray(n);
            row(table, "PriorityQueueCustom", "add + poll (цикл)", n, queueTime(input, false, false), "min-heap");
            row(table, "java.util.PriorityQueue", "add + poll (цикл)", n, queueTime(input, true, false), "min-heap");
            row(table, "HashMapCustom", "put + get + remove", n,
                    mapTime(input, false, HashMapCustom.Probing.LINEAR), "линейное пробирование");
            row(table, "HashMapCustom", "put + get + remove", n,
                    mapTime(input, false, HashMapCustom.Probing.QUADRATIC), "квадратичное пробирование");
            row(table, "java.util.HashMap", "put + get + remove", n,
                    mapTime(input, true, HashMapCustom.Probing.LINEAR), "");
            if (n == 100_000) {
                row(table, "PriorityQueueCustom", "add + poll (цикл)", n,
                        queueTime(input, false, true), "1B: max-heap, тот же вход");
                row(table, "PriorityQueueCustom", "построение через add", n, buildTime(input, false), "1D");
                row(table, "PriorityQueueCustom", "построение heapify", n, buildTime(input, true), "1D");
            }
        }
        table.append('\n');
        appendProbes(table);
        return table.toString();
    }

    private double queueTime(Integer[] input, boolean standard, boolean maxHeap) {
        long expected = standardQueue(input, maxHeap);
        long total = 0;
        for (int run = -1; run < Lab2BenchmarkTest.REPEATS; run++) {
            long start = System.nanoTime();
            long result;
            if (standard) {
                result = standardQueue(input, maxHeap);
            } else {
                result = customQueue(input, maxHeap);
            }
            long elapsed = System.nanoTime() - start;
            assertEquals(expected, result);
            if (run >= 0) {
                total += elapsed;
            }
        }
        return total / Lab2BenchmarkTest.REPEATS / 1_000_000.0;
    }

    private long customQueue(Integer[] input, boolean maxHeap) {
        Comparator<Integer> comparator = null;
        if (maxHeap) {
            comparator = Comparator.reverseOrder();
        }
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        for (Integer value : input) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum = sum * 31 + queue.poll();
        }
        return sum;
    }

    private long standardQueue(Integer[] input, boolean maxHeap) {
        Comparator<Integer> comparator = null;
        if (maxHeap) {
            comparator = Comparator.reverseOrder();
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(11, comparator);
        for (Integer value : input) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum = sum * 31 + queue.poll();
        }
        return sum;
    }

    private double buildTime(Integer[] input, boolean heapify) {
        long total = 0;
        for (int run = -1; run < Lab2BenchmarkTest.REPEATS; run++) {
            long start = System.nanoTime();
            PriorityQueueCustom<Integer> queue;
            if (heapify) {
                queue = new PriorityQueueCustom<>(input);
            } else {
                queue = new PriorityQueueCustom<>();
                for (Integer value : input) {
                    queue.add(value);
                }
            }
            long elapsed = System.nanoTime() - start;
            assertEquals(input.length, queue.size());
            assertEquals(minimum(input), queue.peek());
            if (run >= 0) {
                total += elapsed;
            }
        }
        return total / Lab2BenchmarkTest.REPEATS / 1_000_000.0;
    }

    private int minimum(Integer[] input) {
        int result = input[0];
        for (int value : input) {
            if (value < result) {
                result = value;
            }
        }
        return result;
    }

    private double mapTime(Integer[] input, boolean standard, HashMapCustom.Probing probing) {
        long expected = 0;
        for (int key : input) {
            expected += key;
        }
        long total = 0;
        for (int run = -1; run < Lab2BenchmarkTest.REPEATS; run++) {
            long start = System.nanoTime();
            long result;
            if (standard) {
                result = standardMap(input);
            } else {
                result = customMap(input, probing);
            }
            long elapsed = System.nanoTime() - start;
            assertEquals(expected, result);
            if (run >= 0) {
                total += elapsed;
            }
        }
        return total / Lab2BenchmarkTest.REPEATS / 1_000_000.0;
    }

    private long customMap(Integer[] input, HashMapCustom.Probing probing) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(probing);
        for (Integer key : input) {
            map.put(key, key);
        }
        long sum = 0;
        for (Integer key : input) {
            sum += map.get(key);
        }
        for (Integer key : input) {
            map.remove(key);
        }
        return sum + map.size();
    }

    private long standardMap(Integer[] input) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer key : input) {
            map.put(key, key);
        }
        long sum = 0;
        for (Integer key : input) {
            sum += map.get(key);
        }
        for (Integer key : input) {
            map.remove(key);
        }
        return sum + map.size();
    }

    private void appendProbes(StringBuilder table) {
        Integer[] input = randomArray(50_000);
        table.append("| Пробирование (2B) | Число get | Среднее число проб | Примечание |\n");
        table.append("|---|---:|---:|---|\n");
        for (HashMapCustom.Probing probing : HashMapCustom.Probing.values()) {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>(probing);
            for (Integer key : input) {
                map.put(key, key);
            }
            map.resetProbeStatistics();
            for (int i = 0; i < 25_000; i++) {
                map.get(input[i]);
                int missing = input[i] + 1;
                while (map.containsKey(missing)) {
                    missing++;
                }
                map.get(missing);
            }
            table.append("| ").append(probing).append(" | 50000 | ");
            table.append(Lab2BenchmarkTest.number(map.averageGetProbes()));
            table.append(" | 25000 успешных и 25000 неуспешных запросов |\n");
        }
    }

    private Integer[] randomArray(int n) {
        Random random = new Random(42);
        Integer[] input = new Integer[n];
        for (int i = 0; i < n; i++) {
            input[i] = random.nextInt();
        }
        return input;
    }

    private void row(StringBuilder table, String structure, String operation, int n, double time, String note) {
        table.append("| ").append(structure).append(" | ").append(operation).append(" | ").append(n);
        table.append(" | ").append(Lab2BenchmarkTest.number(time)).append(" | ").append(note).append(" |\n");
    }
}
