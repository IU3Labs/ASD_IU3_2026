package ru.bmstu.lab02.task_2b;

import java.util.Random;

public class Benchmark2B {
    public static void main(String[] args) {
        int n = 50_000;
        Integer[] keys = new Integer[n];
        Random rnd = new Random(42);
        for (int i = 0; i < n; i++) keys[i] = rnd.nextInt(1_000_000);

        //квадратичное пробирование
        HashMapQuadratic<Integer, Integer> quadMap = new HashMapQuadratic<>();
        for (int i = 0; i < n; i++) quadMap.put(keys[i], i);
        for (int i = 0; i < n; i++) quadMap.get(keys[i]);

        System.out.printf("Квадратичное пробирование: среднее число проб = %.4f%n", quadMap.getAverageProbes());

        //линейное пробирование (упрощенная имитация для замера)
        //в реальном коде нужно добавить счетчики в HashMapCustom из 2A
        System.out.println("Линейное пробирование: запустите HashMapCustom из 2A с аналогичным счетчиком.");
    }
}