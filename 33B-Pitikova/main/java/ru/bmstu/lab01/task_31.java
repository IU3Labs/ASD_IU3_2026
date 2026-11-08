package ru.university.acsd.lab01;

import java.util.Scanner;

/**
 * Задача 31. Скалярное произведение.
 * <p>
 * Вход: два массива одинаковой длины n.
 * Выход: скалярное произведение.
 * Сложность: базовая.
 */
public class task_31 {

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
        long[] arrayA = new long[n];
        for (int i = 0; i < n; i++) {
            arrayA[i] = scanner.nextLong();
        }
        
        long dotProduct = 0;
        for (int i = 0; i < n; i++) {
            long b = scanner.nextLong();
            dotProduct += arrayA[i] * b;
        }
        
        System.out.println(dotProduct);
        scanner.close();
    }
}