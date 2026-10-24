package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 * Сложность: O(n).
 */
public final class task_1 {
    public static int[] minMax(int[] array) {
        if (array == null || array.length == 0) throw new IllegalArgumentException("Пустой массив");
        int min = array[0], max = array[0];
        for (int v : array) {
            if (v < min) min = v;
            if (v > max) max = v;
        }
        return new int[]{min, max};
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            int[] res = minMax(a);
            System.out.println(res[0] + " " + res[1]);
        }
    }
}