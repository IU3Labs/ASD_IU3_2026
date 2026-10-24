package ru.university.acsd.lab01;

import java.util.Scanner;

/**
 * Задача 27. Сумма квадратов.
 * <p>
 * Вход: массив из n целых чисел.
 * Выход: сумма квадратов всех элементов.
 * Сложность: базовая.
 */
public class task_27 {

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
        long sumSquares = 0;
        for (int i = 0; i < n; i++) {
            long num = scanner.nextLong();
            sumSquares += num * num;
        }
        System.out.println(sumSquares);
        scanner.close();
    }
}