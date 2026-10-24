package ru.bmstu.lab01.task_3;

import java.util.Scanner;

/**
 * Задача 3. Подсчёт вхождений.
 * Ввод: n, n целых чисел, затем x.
 * Вывод: количество элементов, равных x.
 */
public class Main {
    public static int solve(int[] a, int x) {
        int count = 0;
        for (int value : a) if (value == x) count++;
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        int x = sc.nextInt();
        System.out.println(solve(a, x));
    }
}
