package ru.bmstu.lab1.task_4;

import java.util.Scanner;

/**
 * Задача 5. Сумма элементов на чётных позициях.
 * Вход: массив из n целых чисел.
 * Выход: сумма элементов с чётными индексами: 0, 2, 4 и далее.
 * Сложность: базовая
 */

public class Task4 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            long res = sumEvenIndices(arr);
            System.out.println(res);
        }
    }

    //sumEvenIndices находит сумму элементов с четными индексами.
    public static long sumEvenIndices(int[] arr) {
        sizeCheck(arr.length);

        // Тип long предотвращает переполнение при суммировании значений int.
        long sum = 0;
        for (int i = 0; i < arr.length; i += 2) {
            sum += arr[i];
        }

        return sum;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}