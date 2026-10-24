package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 10. Поиск элемента с условием.
 * Вход: массив из n целых чисел и число k.
 * Выход: индекс последнего элемента, кратного k, или -1.
 * Ограничения: k != 0.
 * Сложность: O(n).
 */
public final class task_10 {
    public static int lastMultiple(int[] array, int k) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] % k == 0) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            int k = sc.nextInt();
            System.out.println(lastMultiple(a, k));
        }
    }
}