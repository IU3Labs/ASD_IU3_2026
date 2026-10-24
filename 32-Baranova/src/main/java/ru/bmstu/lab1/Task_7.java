package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 7. Второй максимум
 * Вход: массив из n целых чисел. Все элементы различны.
 * Выход: второй по величине элемент массива.
 */
public class Task_7 {
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

        int max1 = Math.max(array[0], array[1]);
        int max2 = Math.min(array[0], array[1]);

        for (int i = 2; i < n; i++) {
            if (array[i] > max1) {
                max2 = max1;
                max1 = array[i];
            } else if (array[i] > max2) {
                max2 = array[i];
            }
        }

        System.out.println(max2);
        scanner.close();
    }
}