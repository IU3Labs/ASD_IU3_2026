package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 5. Сумма элементов на чётных позициях
 * Вход: массив из n целых чисел.
 * Выход: сумма элементов с чётными индексами (0, 2, 4, …).
 */
public class Task_5 {
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

        long sum = 0; //

        for (int i = 0; i < n; i += 2) {
            sum += array[i];
        }

        System.out.println(sum);
        scanner.close();
    }
}