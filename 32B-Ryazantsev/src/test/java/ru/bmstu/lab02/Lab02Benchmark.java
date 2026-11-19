package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;

/** Сравнивает структуры на одинаковых данных и печатает таблицу Markdown. */
public final class Lab02Benchmark {
    private static final int WARMUP = 4;
    private static final int SAMPLES = 7;
    private static final int TOP_K = 100;
    private static final long SEED = 3202L;
    private static volatile long resultSink;

    private Lab02Benchmark() {
    }

    /** Запускает полный набор замеров. */
    public static void main(String[] args) {
        System.out.println("# Замеры для ЛР2");
        System.out.println();
        System.out.printf("JDK %s, %s. Прогрев: %d пар запусков, замер: %d пар.%n",
                Runtime.version(), System.getProperty("os.name"), WARMUP, SAMPLES);
        System.out.printf("Seed: %d. Данные готовились заранее. Порядок запуска реализаций чередовался.%n%n", SEED);
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---:|---:|---|");
        for (int size : new int[]{10_000, 50_000, 100_000}) {
            runBaseBenchmarks(size);
        }
        runTopK();
        System.out.println();
        System.out.println("В хеш-таблице хранятся два массива ссылок, тогда как HashMap использует узлы.");
        System.out.println("По этим замерам можно судить о времени, а расход памяти здесь не измерялся.");
        System.out.println("При K = 100 куча просматривает весь вход, но хранит только 100 кандидатов.");
        System.out.println("Сортировка для сравнения копирует весь вход и упорядочивает все элементы.");
    }

    private static void runBaseBenchmarks(int size) {
        Random random = new Random(SEED + size);
        Integer[] values = random.ints(size).boxed().toArray(Integer[]::new);
        Integer[] keys = uniqueRandomKeys(size, random);
        double[] queue = measure(() -> customQueue(values), () -> standardQueue(values));
        row("PriorityQueueCustom", "add + poll", size, queue[0], "N вставок, N извлечений");
        row("java.util.PriorityQueue", "add + poll", size, queue[1], "Тот же массив");

        double[] table = measure(() -> customMap(keys, values), () -> standardMap(keys, values));
        row("HashMapCustom", "put + get", size, table[0], "N вставок, N успешных поисков");
        row("java.util.HashMap", "put + get", size, table[1], "Те же ключи и значения");
    }

    private static Integer[] uniqueRandomKeys(int size, Random random) {
        Integer[] keys = new Integer[size];
        HashSet<Integer> seen = new HashSet<>();
        for (int i = 0; i < size;) {
            int key = random.nextInt();
            if (seen.add(key)) {
                keys[i++] = key;
            }
        }
        return keys;
    }

    private static void runTopK() {
        int size = 1_000_000;
        Integer[] data = new Random(SEED).ints(size).boxed().toArray(Integer[]::new);
        List<Integer> input = Arrays.asList(data);
        Integer[] sorted = sortedCopy(data);
        List<Integer> actual = TopK.topK(input, TOP_K);
        for (int i = 0; i < TOP_K; i++) {
            if (!actual.get(i).equals(sorted[size - 1 - i])) {
                throw new IllegalStateException("Top-K разошёлся с сортировкой");
            }
        }
        double[] times = measure(() -> TopK.topK(input, TOP_K).getLast(),
                () -> sortedCopy(data)[size - TOP_K]);
        row("TopK", "отбор K элементов", size, times[0], "K = 100");
        row("Arrays.sort", "копирование и сортировка", size, times[1], "K = 100");
    }

    private static Integer[] sortedCopy(Integer[] data) {
        Integer[] copy = data.clone();
        Arrays.sort(copy);
        return copy;
    }

    private static long customQueue(Integer[] values) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : values) {
            queue.add(value);
        }
        long checksum = 1;
        while (!queue.isEmpty()) {
            checksum = 31 * checksum + queue.poll();
        }
        return checksum;
    }

    private static long standardQueue(Integer[] values) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer value : values) {
            queue.add(value);
        }
        long checksum = 1;
        while (!queue.isEmpty()) {
            checksum = 31 * checksum + queue.poll();
        }
        return checksum;
    }

    private static long customMap(Integer[] keys, Integer[] values) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        long checksum = 1;
        for (Integer key : keys) {
            checksum = 31 * checksum + map.get(key);
        }
        return checksum;
    }

    private static long standardMap(Integer[] keys, Integer[] values) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        long checksum = 1;
        for (Integer key : keys) {
            checksum = 31 * checksum + map.get(key);
        }
        return checksum;
    }

    private static double[] measure(LongSupplier custom, LongSupplier standard) {
        if (custom.getAsLong() != standard.getAsLong()) {
            throw new IllegalStateException("Контрольные результаты различаются");
        }
        double[] total = new double[2];
        for (int i = 0; i < WARMUP + SAMPLES; i++) {
            int first = i % 2;
            long firstTime = elapsed(first == 0 ? custom : standard);
            long secondTime = elapsed(first == 0 ? standard : custom);
            if (i >= WARMUP) {
                total[first] += firstTime / 1_000_000.0 / SAMPLES;
                total[1 - first] += secondTime / 1_000_000.0 / SAMPLES;
            }
        }
        return total;
    }

    private static long elapsed(LongSupplier operation) {
        long start = System.nanoTime();
        resultSink = operation.getAsLong();
        return System.nanoTime() - start;
    }

    private static void row(String implementation, String operation, int size, double time, String note) {
        System.out.printf(Locale.ROOT, "| `%s` | %s | %d | %.3f | %s |%n",
                implementation, operation, size, time, note);
    }
}
