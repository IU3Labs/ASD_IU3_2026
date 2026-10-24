package ru.bmstu.lab01.task_2;

import java.util.Scanner;

/**
 * Задача 2. Индекс первого вхождения.
 * Ввод: n, n целых чисел, затем x.
 * Вывод: индекс первого вхождения x или -1.
 */
public class Main {
    public static int solve(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) return i;
        }
        return -1;
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
