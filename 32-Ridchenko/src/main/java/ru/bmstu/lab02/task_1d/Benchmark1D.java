package ru.bmstu.lab02.task_1d;

import java.util.Random;

public class Benchmark1D {
    public static void main(String[] args) {
        int n = 100_000;
        Integer[] data = new Integer[n];
        Random rnd = new Random(42);
        for (int i = 0; i < n; i++) data[i] = rnd.nextInt();

        //замер через add
        long start = System.nanoTime();
        PriorityQueueCustom<Integer> pq1 = new PriorityQueueCustom<>();
        for (Integer x : data) pq1.add(x);
        long timeAdd = System.nanoTime() - start;

        //замер через конструктор O(n)
        start = System.nanoTime();
        PriorityQueueCustom<Integer> pq2 = new PriorityQueueCustom<>(data);
        long timeConstruct = System.nanoTime() - start;

        System.out.printf("Последовательные add: %.2f мс (размер: %d)%n", timeAdd / 1e6, pq1.size());
        System.out.printf("Построение за O(n): %.2f мс (размер: %d)%n", timeConstruct / 1e6, pq2.size());
    }
}