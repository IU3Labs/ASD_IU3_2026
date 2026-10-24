package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 26. Произведение элементов
 * Вход: массив из n целых чисел.
 * Выход: произведение всех элементов по модулю 10^9 + 7.
 */
public class Task_26 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        long[] array = new long[n];

        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextLong();
        }

        long mod = 1_000_000_007;
        long product = 1;

        for (int i = 0; i < n; i++) {
            long val = (array[i] % mod + mod) % mod;
            product = (product * val) % mod;
        }

        System.out.println(product);
        scanner.close();
    }
}