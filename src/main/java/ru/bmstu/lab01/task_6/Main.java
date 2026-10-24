package ru.bmstu.lab01.task_6;

import java.util.Scanner;

/**
 * Задача 6. Элементы больше среднего.
 * Ввод: n, затем n целых чисел.
 * Вывод: количество элементов, строго больших среднего арифметического.
 */
public class Main {
    public static int solve(int[] a) {
        long sum = 0;
        for (int value : a) sum += value;
        int count = 0;
        // Сравнение a[i] * n с суммой позволяет избежать ошибок округления.
        for (int value : a) {
            if ((long) value * a.length > sum) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        System.out.println(solve(a));
    }
}
