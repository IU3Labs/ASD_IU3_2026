package ru.university.acsd.lab01;

import java.util.Scanner;

/**
 * Задача 36. Бегущая сумма.
 * <p>
 * Вход: массив из n целых чисел.
 * Выход: массив, где i-й элемент — сумма первых i + 1 элементов.
 * Сложность: базовая.
 */
public class task_36 {

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
        long currentSum = 0;
        for (int i = 0; i < n; i++) {
            currentSum += scanner.nextLong();
            System.out.print(currentSum + " ");
        }
        System.out.println();
        scanner.close();
    }
}