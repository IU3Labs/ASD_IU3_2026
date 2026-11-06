package ru.bmstu.lab1.task_6;

import java.util.Scanner;

/**
 * Задача 10. Поиск элемента с условием.
 * Вход: массив из n целых чисел и число K.
 * Выход: индекс последнего элемента, кратного k. Если такого нет - вывести -1.
 * Сложность: базовая
 */

public class Task6 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            int k = scan.nextInt();
            int res = findLastMultipleIndex(arr, k);
            System.out.println(res);
        }
    }

    //findLastMultipleIndex находит индекс последнего элемента, кратного k.
    public static int findLastMultipleIndex(int[] arr, int k) {
        sizeCheck(arr.length);

        if (k == 0) {
            throw new IllegalArgumentException("The divisor cannot be equal to zero.");
        }

        // Просмотр справа налево позволяет сразу найти последнее вхождение.
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] % k == 0) {
                return i;
            }
        }

        return -1;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99_999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}