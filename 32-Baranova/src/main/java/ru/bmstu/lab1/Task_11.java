package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 11. Ближайший к среднему
 * Вход: массив из n целых чисел.
 * Выход: элемент массива, ближайший к среднему арифметическому.
 */
public class Task_11 {
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

        double average = (double) sum / n;

        int closestElement = array[0];
        double minDifference = Math.abs(array[0] - average);

        for (int i = 1; i < n; i++) {
            double diff = Math.abs(array[i] - average);
            if (diff < minDifference) {
                minDifference = diff;
                closestElement = array[i];
            }
        }

        System.out.println(closestElement);
        scanner.close();
    }
}