package ru.university.acsd.lab01;

import java.util.Scanner;

/**
 * Задача 26. Произведение элементов.
 * <p>
 * Вход: массив из n целых чисел.
 * Выход: произведение всех элементов по модулю 10^9 + 7.
 * Сложность: базовая.
 */
public class task_26 {

    private static final long MOD = 1_000_000_007L;

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }
        int n = scanner.nextInt();
        long product = 1;
        for (int i = 0; i < n; i++) {
            long num = scanner.nextLong();
            long safeNum = (num % MOD + MOD) % MOD;
            product = (product * safeNum) % MOD;
        }
        System.out.println(product);
        scanner.close();
    }
}