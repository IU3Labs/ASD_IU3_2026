
package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Сравнение производительности собственных структур
 * со стандартными реализациями Java.
 */
public final class Benchmarks {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int REPETITIONS = 5;
    private static volatile long checksum;

    private Benchmarks() {
    }

    public static void main(String[] args) {
        System.out.println("Время в миллисекундах, среднее за "
                + REPETITIONS + " запусков.");
        System.out.println("Размер | Реализация | Собственная | Стандартная");

        for (int n : SIZES) {
            benchmarkQueue(n);
            benchmarkMap(n);
            benchmarkTopK(n);
        }

        System.out.println("Контрольная сумма: " + checksum);
    }

    private static void benchmarkQueue(int n) {
        int[] data = createData(n);
        double custom = measure(() -> {
            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

            for (int value : data) {
                queue.add(value);
            }

            long sum = 0;
            while (!queue.isEmpty()) {
                sum += queue.poll();
            }
            checksum = sum;
        });

        double standard = measure(() -> {
            PriorityQueue<Integer> queue = new PriorityQueue<>();

            for (int value : data) {
                queue.add(value);
            }

            long sum = 0;
            while (!queue.isEmpty()) {
                sum += queue.poll();
            }
            checksum = sum;
        });

        print(n, "PriorityQueue", custom, standard);
    }

    private static void benchmarkMap(int n) {
        double custom = measure(() -> {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

            for (int i = 0; i < n; i++) {
                map.put(i, i);
            }

            long sum = 0;
            for (int i = 0; i < n; i++) {
                sum += map.get(i);
            }
            checksum = sum;
        });

        double standard = measure(() -> {
            HashMap<Integer, Integer> map = new HashMap<>();

            for (int i = 0; i < n; i++) {
                map.put(i, i);
            }

            long sum = 0;
            for (int i = 0; i < n; i++) {
                sum += map.get(i);
            }
            checksum = sum;
        });

        print(n, "HashMap", custom, standard);
    }

    private static void benchmarkTopK(int n) {
        int[] data = createData(n);
        int k = Math.min(100, n);

        double custom = measure(() -> {
            int[] result = TopK.topK(data, k);
            checksum = Arrays.hashCode(result);
        });

        double standard = measure(() -> {
            PriorityQueue<Integer> queue = new PriorityQueue<>();

            for (int value : data) {
                if (queue.size() < k) {
                    queue.add(value);
                } else if (value > queue.peek()) {
                    queue.poll();
                    queue.add(value);
                }
            }

            int[] result = new int[k];
            for (int i = k - 1; i >= 0; i--) {
                result[i] = queue.poll();
            }

            checksum = Arrays.hashCode(result);
        });

        print(n, "TopK", custom, standard);
    }

    private static int[] createData(int n) {
        Random random = new Random(42);
        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt();
        }

        return data;
    }

    private static double measure(Runnable action) {
        for (int i = 0; i < 3; i++) {
            action.run();
        }

        long total = 0;

        for (int i = 0; i < REPETITIONS; i++) {
            long start = System.nanoTime();
            action.run();
            total += System.nanoTime() - start;
        }

        return total / (REPETITIONS * 1_000_000.0);
    }

    private static void print(
            int n, String name, double custom, double standard) {
        System.out.printf(
                "%6d | %-13s | %10.3f | %10.3f%n",
                n, name, custom, standard
        );
    }
}
