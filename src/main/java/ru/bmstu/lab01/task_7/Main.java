package ru.bmstu.lab01.task_7;

import java.util.Scanner;

/**
 * Задача 7. Второй максимум.
 * Ввод: n, затем n различных целых чисел.
 * Вывод: второй по величине элемент.
 */
public class Main {
    public static int solve(int[] a) {
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        for (int value : a) {
            if (value > max1) {
                max2 = max1;
                max1 = value;
            } else if (value > max2) {
                max2 = value;
            }
        }
        return max2;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        System.out.println(solve(a));
    }
}
