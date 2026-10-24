package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 27. Сумма квадратов
 * Вход: массив из n целых чисел.
 * Выход: сумма квадратов всех элементов.
 */
public class Task_27 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        long sumOfSquares = 0;

        for (int i = 0; i < n; i++) {
            long val = array[i];
            sumOfSquares += val * val;
        }

        System.out.println(sumOfSquares);
        scanner.close();
    }
}