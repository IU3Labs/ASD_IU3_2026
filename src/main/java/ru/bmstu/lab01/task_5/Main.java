package ru.bmstu.lab01.task_5;

import java.util.Scanner;

/**
 * Задача 5. Сумма элементов на чётных индексах (0, 2, 4, ...).
 * Ввод: n, затем n целых чисел.
 * Вывод: сумма элементов с чётными индексами.
 */
public class Main {
    public static long solve(int[] a) {
        long sum = 0;
        for (int i = 0; i < a.length; i += 2) sum += a[i];
        return sum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        System.out.println(solve(a));
    }
}
