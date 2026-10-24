package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 6. Элементы больше среднего
 * Вход: массив из n целых чисел.
 * Выход: количество элементов, строго больших среднего арифметического всех элементов.
 */
public class Task_6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        int[] array = new int[n];
        long sum = 0;

        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
            sum += array[i];
        }

        long average = sum / n;
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (array[i] > average) {
                count++;
            }
        }

        System.out.println(count);
        scanner.close();
    }
}