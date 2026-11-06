package ru.bmstu.lab1.task_13;

import java.util.Scanner;

/**
 * Задача 25. Сумма элементов
 * Вход: массив из n целых чисел.
 * Выход: сумма всех элементов.
 * Сложность: базовая
 */

public class Task13 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            long res = sumElements(arr);
            System.out.println(res);
        }
    }

    //sumElements находит сумму всех элементов массива.
    public static long sumElements(int[] arr) {
        sizeCheck(arr.length);

        // Тип long предотвращает переполнение при суммировании значений int.
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        return sum;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99_999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}