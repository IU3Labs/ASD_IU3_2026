package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

/** Замеры очередей; стандартная коллекция используется только для сравнения. */
public final class QueueBenchmark {
    private static final int WARMUP_RUNS = 5;
    private static final int MEASURE_RUNS = 10;
    private static final int RANDOM_SEED = 31;
    private static volatile long checksum;

    private QueueBenchmark() {
    }

    /** Печатает Markdown-таблицы со средними временами в миллисекундах. */
    public static void main(String[] args) {
        System.out.println("# Замеры производительности");
        System.out.println();
        System.out.println("Java: " + System.getProperty("java.version")
                + "; ОС: " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + ".");
        System.out.println("Прогревов: " + WARMUP_RUNS + "; измерений: " + MEASURE_RUNS
                + "; seed: " + RANDOM_SEED + ".");
        System.out.println("Данные создаются до таймера. В таблице среднее время;"
                + " подготовка данных не учитывается.");
        System.out.println();
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---:|---:|---|");
        for (int n : new int[] {10000, 50000, 100000}) {
            Integer[] values = createValues(n);
            measureCycle(values, false);
            measureCycle(values, true);
        }
        System.out.println();
        System.out.println("## Построение из 100 000 убывающих элементов, без извлечения");
        System.out.println();
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---:|---:|---|");
        measureBuilding(createDescendingValues(100000));
        System.out.println();
        System.out.println("Контрольная сумма: " + checksum + ".");
    }

    private static Integer[] createValues(int n) {
        Random random = new Random(RANDOM_SEED);
        Integer[] values = new Integer[n];
        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt();
        }
        return values;
    }

    private static void measureCycle(Integer[] values, boolean maximum) {
        double own = 0;
        double standard = 0;
        for (int run = -WARMUP_RUNS; run < MEASURE_RUNS; run++) {
            // Чередуем порядок, чтобы одна реализация не запускалась всегда первой.
            long first;
            long second;
            if (run % 2 == 0) {
                first = runOwn(values, maximum);
                second = runStandard(values, maximum);
            } else {
                second = runStandard(values, maximum);
                first = runOwn(values, maximum);
            }
            if (run >= 0) {
                own += first;
                standard += second;
            }
        }
        String mode = maximum ? "max-heap" : "min-heap";
        printRow("PriorityQueueCustom", "add всех + poll всех", values.length, own, mode);
        printRow("java.util.PriorityQueue", "add всех + poll всех", values.length, standard, mode);
    }

    private static long runOwn(Integer[] values, boolean maximum) {
        long start = System.nanoTime();
        Comparator<Integer> comparator = maximum ? Comparator.reverseOrder() : Comparator.naturalOrder();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        for (Integer value : values) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        long elapsed = System.nanoTime() - start;
        checksum = sum;
        return elapsed;
    }

    private static long runStandard(Integer[] values, boolean maximum) {
        long start = System.nanoTime();
        Comparator<Integer> comparator = maximum ? Comparator.reverseOrder() : Comparator.naturalOrder();
        PriorityQueue<Integer> queue = new PriorityQueue<>(comparator);
        for (Integer value : values) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        long elapsed = System.nanoTime() - start;
        checksum = sum;
        return elapsed;
    }

    private static Integer[] createDescendingValues(int n) {
        Integer[] values = new Integer[n];
        for (int i = 0; i < n; i++) {
            values[i] = n - i;
        }
        return values;
    }

    private static void measureBuilding(Integer[] values) {
        double adds = 0;
        double heapify = 0;
        double standard = 0;
        for (int run = -WARMUP_RUNS; run < MEASURE_RUNS; run++) {
            long first = buildByAdding(values);
            long second = buildHeap(values);
            long third = buildStandard(values);
            if (run >= 0) {
                adds += first;
                heapify += second;
                standard += third;
            }
        }
        printRow("PriorityQueueCustom", "последовательные add", values.length, adds, "O(n log n), верхняя оценка");
        printRow("PriorityQueueCustom", "конструктор из массива", values.length, heapify, "O(n)");
        printRow("java.util.PriorityQueue", "конструктор из коллекции", values.length, standard, "O(n)");
    }

    private static long buildByAdding(Integer[] values) {
        long start = System.nanoTime();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : values) {
            queue.add(value);
        }
        long elapsed = System.nanoTime() - start;
        checksum = queue.size() + (long) queue.peek();
        return elapsed;
    }

    private static long buildHeap(Integer[] values) {
        long start = System.nanoTime();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);
        long elapsed = System.nanoTime() - start;
        checksum = queue.size() + (long) queue.peek();
        return elapsed;
    }

    private static long buildStandard(Integer[] values) {
        long start = System.nanoTime();
        PriorityQueue<Integer> queue = new PriorityQueue<>(Arrays.asList(values));
        long elapsed = System.nanoTime() - start;
        checksum = queue.size() + (long) queue.peek();
        return elapsed;
    }

    private static void printRow(String structure, String operation, int n, double total, String note) {
        double milliseconds = total / MEASURE_RUNS / 1000000;
        System.out.printf(Locale.ROOT, "| %s | %s | %d | %.3f | %s |%n",
                structure, operation, n, milliseconds, note);
    }
}
