package ru.bmstu.lab1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Задача 19. Объединение двух отсортированных массивов.
 * Вход: два массива, отсортированные по неубыванию.
 * Выход: один отсортированный массив — результат слияния.
 * Требование: O(n + m).
 */
public final class task_19 {
    public static int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            res[k++] = (a[i] <= b[j]) ? a[i++] : b[j++];
        }
        while (i < a.length) res[k++] = a[i++];
        while (j < b.length) res[k++] = b[j++];
        return res;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            int m = sc.nextInt();
            int[] b = new int[m];
            for (int i = 0; i < m; i++) b[i] = sc.nextInt();
            System.out.println(Arrays.toString(merge(a, b)));
        }
    }
}