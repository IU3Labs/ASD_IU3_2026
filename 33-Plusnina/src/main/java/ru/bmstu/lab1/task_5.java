package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 5. Сумма элементов на чётных позициях.
 * Вход: массив из n целых чисел.
 * Выход: сумма элементов с чётными индексами (0, 2, 4, ...).
 * Сложность: O(n).
 */
public final class task_5 {
    public static long sumEvenIndices(int[] array) {
        long sum = 0;
        for (int i = 0; i < array.length; i += 2) {
            sum += array[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            System.out.println(sumEvenIndices(a));
        }
    }
}