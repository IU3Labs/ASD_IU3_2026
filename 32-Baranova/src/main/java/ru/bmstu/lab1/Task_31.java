package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 31. Скалярное произведение
 * Вход: два массива одинаковой длины n.
 * Выход: скалярное произведение (сумма попарных произведений).
 */
public class Task_31 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }

        long scalarProduct = 0;

        for (int i = 0; i < n; i++) {
            scalarProduct += (long) a[i] * b[i];
        }

        System.out.println(scalarProduct);
        scanner.close();
    }
}