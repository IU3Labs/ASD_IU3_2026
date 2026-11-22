package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

public final class BenchmarkRunner {
    private static volatile long sink;

    private BenchmarkRunner() {
    }

    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---:|---:|---|");
        for (int n : new int[]{10000, 50000, 100000}) {
            Integer[] data = randomData(n);
            print("Task1D_PriorityQueue", "add + poll", n, () -> customHeap(data), "min-heap");
            print("java.util.PriorityQueue", "add + poll", n, () -> javaHeap(data), "min-heap");
            print("Task2A_HashMap", "put + get", n, () -> customMap(data), "линейное пробирование");
            print("Task2B_QuadraticHashMap", "put + get", n, () -> quadraticMap(data), "квадратичное пробирование");
            print("java.util.HashMap", "put + get", n, () -> javaMap(data), "стандартная реализация");
        }
        Integer[] data = randomData(100000);
        System.out.println("\n## Сравнение вариантов кучи (100 000 элементов)");
        System.out.println("| Вариант | Среднее время (мс) |");
        System.out.println("|---|---:|");
        printSimple("min-heap", () -> customHeap(data));
        printSimple("max-heap", () -> customMaxHeap(data));
        printSimple("100 000 последовательных add", () -> addOnly(data));
        printSimple("Построение из массива O(n)", () -> buildHeap(data));

        System.out.println("\n## Пробирования get (50 000 поисков)");
        System.out.println("| Таблица | Среднее пробирований |");
        System.out.println("|---|---:|");
        Task2A_HashMap<Integer, Integer> linear = new Task2A_HashMap<>();
        Task2B_QuadraticHashMap<Integer, Integer> quadratic = new Task2B_QuadraticHashMap<>();
        for (int i = 0; i < 100000; i++) {
            linear.put(data[i], i);
            quadratic.put(data[i], i);
        }
        long lp = 0;
        long qp = 0;
        for (int i = 0; i < 50000; i++) {
            lp += linear.getProbeCount(data[i]);
            qp += quadratic.getProbeCount(data[i]);
        }
        System.out.printf(java.util.Locale.US, "| Линейная | %.3f |%n", lp / 50000.0);
        System.out.printf(java.util.Locale.US, "| Квадратичная | %.3f |%n", qp / 50000.0);

        System.out.println("\n## Порог загрузки (100 000 вставок, 50 000 поисков)");
        System.out.println("| Порог | Ёмкость | put, среднее мкс/операцию | get, среднее мкс/операцию |");
        System.out.println("|---|---:|---:|---:|");
        for (double threshold : new double[]{0.5, 0.9}) {
            long put = 0;
            long get = 0;
            int capacity = 0;
            for (int repeat = 0; repeat < 5; repeat++) {
                Task2D_LoadFactorHashMap<Integer, Integer> map = new Task2D_LoadFactorHashMap<>(threshold);
                long start = System.nanoTime();
                for (int i = 0; i < 100000; i++) {
                    map.put(data[i], i);
                }
                put += System.nanoTime() - start;
                start = System.nanoTime();
                for (int i = 0; i < 50000; i++) {
                    sink += map.get(data[i]) == null ? 0 : 1;
                }
                get += System.nanoTime() - start;
                capacity = map.capacity();
            }
            System.out.printf(java.util.Locale.US, "| %.1f | %d | %.3f | %.3f |%n",
                    threshold, capacity, put / 5.0 / 100000 / 1000, get / 5.0 / 50000 / 1000);
        }
        System.out.println("\n## Искусственные коллизии");
        System.out.println("| Набор | Операция | N | Время (мс) |");
        System.out.println("|---|---|---:|---:|");
        for (int n : new int[]{1000, 10000}) {
            final int count = n;
            printCollision("Обычные ключи", n, () -> normalKeys(count));
            printCollision("Task2C_CollisionKey", n, () -> collisionKeys(count));
        }
    }

    private static Integer[] randomData(int n) {
        Random random = new Random(12345);
        Integer[] values = new Integer[n];
        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt();
        }
        return values;
    }

    private static void customHeap(Integer[] data) {
        Task1D_PriorityQueue<Integer> queue = new Task1D_PriorityQueue<>();
        for (int value : data) queue.add(value);
        while (!queue.isEmpty()) sink += queue.poll();
    }

    private static void customMaxHeap(Integer[] data) {
        Task1B_PriorityQueue<Integer> queue = new Task1B_PriorityQueue<>(Comparator.reverseOrder());
        for (int value : data) queue.add(value);
        while (!queue.isEmpty()) sink += queue.poll();
    }

    private static void javaHeap(Integer[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int value : data) queue.add(value);
        while (!queue.isEmpty()) sink += queue.poll();
    }

    private static void addOnly(Integer[] data) {
        Task1D_PriorityQueue<Integer> queue = new Task1D_PriorityQueue<>();
        for (int value : data) queue.add(value);
        sink += queue.size();
    }

    private static void buildHeap(Integer[] data) {
        Task1D_PriorityQueue<Integer> queue = new Task1D_PriorityQueue<>(data);
        sink += queue.size();
    }

    private static void customMap(Integer[] data) {
        Task2A_HashMap<Integer, Integer> map = new Task2A_HashMap<>();
        for (int i = 0; i < data.length; i++) map.put(data[i], i);
        for (int value : data) sink += map.get(value);
    }

    private static void quadraticMap(Integer[] data) {
        Task2B_QuadraticHashMap<Integer, Integer> map = new Task2B_QuadraticHashMap<>();
        for (int i = 0; i < data.length; i++) map.put(data[i], i);
        for (int value : data) sink += map.get(value);
    }

    private static void javaMap(Integer[] data) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < data.length; i++) map.put(data[i], i);
        for (int value : data) sink += map.get(value);
    }

    private static void normalKeys(int n) {
        Task2A_HashMap<String, Integer> map = new Task2A_HashMap<>();
        for (int i = 0; i < n; i++) map.put("key" + i, i);
        for (int i = 0; i < n; i++) sink += map.get("key" + i);
        for (int i = 0; i < n; i++) map.remove("key" + i);
    }

    private static void collisionKeys(int n) {
        Task2A_HashMap<Task2C_CollisionKey, Integer> map = new Task2A_HashMap<>();
        for (int i = 0; i < n; i++) map.put(new Task2C_CollisionKey("key" + i), i);
        for (int i = 0; i < n; i++) sink += map.get(new Task2C_CollisionKey("key" + i));
        for (int i = 0; i < n; i++) map.remove(new Task2C_CollisionKey("key" + i));
    }

    private static double measure(Runnable task) {
        for (int i = 0; i < 2; i++) task.run();
        long total = 0;
        for (int i = 0; i < 5; i++) {
            long start = System.nanoTime();
            task.run();
            total += System.nanoTime() - start;
        }
        return total / 5_000_000.0;
    }

    private static void print(String name, String operation, int n, Runnable task, String note) {
        System.out.printf(java.util.Locale.US, "| %s | %s | %d | %.3f | %s |%n",
                name, operation, n, measure(task), note);
    }

    private static void printSimple(String name, Runnable task) {
        System.out.printf(java.util.Locale.US, "| %s | %.3f |%n", name, measure(task));
    }

    private static void printCollision(String name, int n, Runnable task) {
        System.out.printf(java.util.Locale.US, "| %s | put + get + remove | %d | %.3f |%n", name, n, measure(task));
    }
}
