package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 9. Проверка отсортированности.
 * Вход: массив из n целых чисел.
 * Выход: YES, если массив неубывающий; NO иначе.
 * Сложность: O(n).
 */
public final class task_9 {
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            System.out.println(isSorted(a) ? "YES" : "NO");
        }
    }
}