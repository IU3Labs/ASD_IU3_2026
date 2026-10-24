package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 3. Подсчёт вхождений
 * Вход: массив из n целых чисел и число x.
 * Выход: количество элементов, равных x.
 */
public class Task_3 {
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

        if (!scanner.hasNextInt()) {
            return;
        }
        int x = scanner.nextInt();

        int count = 0; //

        for (int i = 0; i < n; i++) {
            if (array[i] == x) {
                count++; //
            }
        }

        System.out.println(count);
        scanner.close();
    }
}