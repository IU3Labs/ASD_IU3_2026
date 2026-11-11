package ru.bmstu.lab01.task_1;

import java.util.Scanner;

/**
 * Задача 1. Максимум и минимум.
 * Ввод: n, затем n целых чисел.
 * Вывод: минимум и максимум через пробел.
 */
public class Main {
    public static int[] solve(int[] a) {
        int min = a[0], max = a[0];
        for (int value : a) {
            if (value < min) min = value;
            if (value > max) max = value;
        }
        return new int[]{min, max};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        int[] result = solve(a);
        System.out.println(result[0] + " " + result[1]);
    }
}
