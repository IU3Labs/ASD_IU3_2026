package ru.bmstu.lab1.task_01;

import java.util.Scanner;

/**
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел (1 < n < 10^5).
 * Выход: минимальный и максимальный элементы массива.
 */
public final class Main {

    private Main() {
    }

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
        int first = scanner.nextInt();
        int min = first;
        int max = first;

        for (int i = 1; i < n; i++) {
            int current = scanner.nextInt();
            if (current < min) {
                min = current;
            }
            if (current > max) {
                max = current;
            }
        }
        System.out.println(min + " " + max);
    }
}