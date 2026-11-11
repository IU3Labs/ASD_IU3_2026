package ru.bmstu.lab1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Задача 13. Сортировка выбором.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию.
 * Требование: реализовать сортировку выбором вручную.
 * Сложность: O(n^2).
 */
public final class task_13 {
    public static void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIdx]) minIdx = j;
            }
            int temp = array[i];
            array[i] = array[minIdx];
            array[minIdx] = temp;
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            sort(a);
            System.out.println(Arrays.toString(a));
        }
    }
}