package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 2. Индекс первого вхождения.
 * Вход: массив из n целых чисел и число x.
 * Выход: индекс первого элемента, равного x, или -1.
 * Сложность: O(n).
 */
public final class task_2 {
    public static int firstIndexOf(int[] array, int x) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            int x = sc.nextInt();
            System.out.println(firstIndexOf(a, x));
        }
    }
}