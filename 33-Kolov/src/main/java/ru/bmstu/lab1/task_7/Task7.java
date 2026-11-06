package ru.bmstu.lab1.task_7;

import java.util.Scanner;

/**
 * Задача 11. Ближайший к среднему.
 * Вход: массив из n целых чисел.
 * Выход: элемент, ближайший к среднему арифметическому. Если несколько — вывести первый по индексу.
 * Ограничения: среднее может быть дробным.
 * Сложность: базовая
 */

public class Task7 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            int res = findClosestToAverage(arr);
            System.out.println(res);
        }
    }

    //findClosestToAverage находит элемент, ближайший к среднему арифметическому.
    public static int findClosestToAverage(int[] arr) {
        sizeCheck(arr.length);

        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        int res = arr[0];
        // Сравниваем расстояния, умноженные на n, без дробного деления.
        long minDistance = Math.abs((long) arr[0] * arr.length - sum);

        for (int i = 1; i < arr.length; i++) {
            long distance = Math.abs((long) arr[i] * arr.length - sum);
            if (distance < minDistance) {
                minDistance = distance;
                res = arr[i];
            }
        }

        return res;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}