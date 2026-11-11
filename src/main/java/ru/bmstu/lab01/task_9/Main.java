package ru.bmstu.lab01.task_9;

import java.util.Scanner;

/**
 * Задача 9. Проверка отсортированности.
 * Ввод: n, затем n целых чисел.
 * Вывод: YES, если массив неубывающий; иначе NO.
 */
public class Main {
    public static boolean solve(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        System.out.println(solve(a) ? "YES" : "NO");
    }
}
