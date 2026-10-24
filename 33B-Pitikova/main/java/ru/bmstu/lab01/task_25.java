package ru.university.acsd.lab01;

import java.util.Scanner;

/**
 * Задача 25. Сумма элементов.
 * <p>
 * Вход: массив из n целых чисел.
 * Выход: сумма всех элементов.
 * Сложность: базовая.
 */
public class task_25 {

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
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += scanner.nextLong();
        }
        System.out.println(sum);
        scanner.close();
    }
}