package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Замеры для benchmarks.md: наши структуры против стандартной библиотеки
 * на наборах 10 000, 50 000 и 100 000 элементов.
 * Результат печатается строками markdown-таблицы.
 */
public class Benchmarks {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_RUNS = 5;
    private static final int RUNS = 10;
    private static final int TOP_K = 100;
    private static final long SEED = 2026L;
    private static final double NANOS_PER_MS = 1_000_000.0;

    /** Накапливаем результаты, чтобы JIT не выбросил работу как неиспользуемую. */
    private static long sink;

    private Benchmarks() {
    }

    /** Печатает markdown-таблицу со средним временем операций. */
    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("| --- | --- | --- | --- | --- |");

        for (int n : SIZES) {
            int[] data = randomData(n);
            benchmarkQueues(n, data);
            benchmarkMaps(n, data);
            benchmarkTopK(n, data);
        }

        System.err.println("checksum (игнорировать): " + sink);
    }

    private static int[] randomData(int n) {
        Random random = new Random(SEED);
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    private static void benchmarkQueues(int n, int[] data) {
        row("PriorityQueueCustom", "add + poll (цикл)", n, () -> {
            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
            for (int value : data) {
                queue.add(value);
            }
            while (!queue.isEmpty()) {
                sink += queue.poll();
            }
        });
        row("java.util.PriorityQueue", "add + poll (цикл)", n, () -> {
            PriorityQueue<Integer> queue = new PriorityQueue<>();
            for (int value : data) {
                queue.add(value);
            }
            while (!queue.isEmpty()) {
                sink += queue.poll();
            }
        });
    }

    private static void benchmarkMaps(int n, int[] data) {
        row("HashMapCustom", "put + get (все ключи)", n, () -> {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
            for (int value : data) {
                map.put(value, value);
            }
            for (int value : data) {
                sink += map.get(value);
            }
        });
        row("java.util.HashMap", "put + get (все ключи)", n, () -> {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int value : data) {
                map.put(value, value);
            }
            for (int value : data) {
                sink += map.get(value);
            }
        });
    }

    private static void benchmarkTopK(int n, int[] data) {
        List<Integer> list = new ArrayList<>(n);
        for (int value : data) {
            list.add(value);
        }
        row("TopK (куча)", "topK, k=" + TOP_K, n, () -> sink += TopK.topK(list, TOP_K).size());
        row("Collections.sort", "сортировка + первые k=" + TOP_K, n, () -> {
            List<Integer> copy = new ArrayList<>(list);
            copy.sort(Collections.reverseOrder());
            sink += copy.subList(0, TOP_K).size();
        });
    }

    private static void row(String structure, String operation, int n, Runnable task) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            task.run();
        }
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            task.run();
            total += System.nanoTime() - start;
        }
        double millis = total / (double) RUNS / NANOS_PER_MS;
        System.out.printf(Locale.US, "| `%s` | %s | %d | %.2f | |%n", structure, operation, n, millis);
    }
}
