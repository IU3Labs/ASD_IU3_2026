package ru.bmstu.lab01.task_10;

import java.util.Scanner;

/**
 * Задача 10. Индекс последнего элемента, кратного k.
 * Ввод: n, n целых чисел, затем k (k != 0).
 * Вывод: индекс последнего элемента, делящегося на k, или -1.
 */
public class Main {
    public static int solve(int[] a, int k) {
        int index = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] % k == 0) index = i;
        }
        return index;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        int k = sc.nextInt();
        System.out.println(solve(a, k));
    }
}
