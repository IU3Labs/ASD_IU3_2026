package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Замер времени topK на 1 000 000 элементов и сравнение с решением через полную сортировку.
 * Запуск: main из IDE либо
 * java -cp target/classes:target/test-classes ru.bmstu.lab02.TopKBenchmark после mvn test-compile.
 */
public class TopKBenchmark {

    private static final int N = 1_000_000;
    private static final int RUNS = 5;

    /** Печатает таблицу времени topK и сортировки для разных k. */
    public static void main(String[] args) {
        Random random = new Random(12345);
        List<Integer> data = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            data.add(random.nextInt());
        }

        int[] ks = {10, 100, 1_000, 10_000, 100_000};

        // Прогрев JIT
        for (int i = 0; i < 3; i++) {
            TopK.topK(data, 100);
            sortBased(data, 100);
        }

        System.out.printf("N = %d, среднее по %d запускам%n", N, RUNS);
        System.out.printf("%-10s %-18s %-18s%n", "k", "topK (куча), мс", "сортировка, мс");

        for (int k : ks) {
            double heapMs = measure(() -> TopK.topK(data, k));
            double sortMs = measure(() -> sortBased(data, k));
            System.out.printf("%-10d %-18.2f %-18.2f%n", k, heapMs, sortMs);
        }
    }

    private static List<Integer> sortBased(List<Integer> data, int k) {
        List<Integer> copy = new ArrayList<>(data);
        Collections.sort(copy, Collections.reverseOrder());
        return new ArrayList<>(copy.subList(0, Math.min(k, copy.size())));
    }

    private static double measure(Runnable task) {
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            task.run();
            total += System.nanoTime() - start;
        }
        return total / (double) RUNS / 1_000_000.0;
    }
}
