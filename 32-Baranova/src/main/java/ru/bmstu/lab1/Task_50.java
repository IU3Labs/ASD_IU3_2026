package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 50. Сумма столбцов матрицы
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из m элементов — сумм столбцов матрицы.
 */
public class Task_50 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        long[] colSums = new long[m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int value = scanner.nextInt();
                colSums[j] += value;
            }
        }

        for (int j = 0; j < m; j++) {
            System.out.print(colSums[j]);
            if (j < m - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}