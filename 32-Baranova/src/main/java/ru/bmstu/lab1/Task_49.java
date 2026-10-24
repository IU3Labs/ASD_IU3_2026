package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 49. Сумма строк матрицы
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из n элементов — сумм строк матрицы.
 */
public class Task_49 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] matrix = new int[n][m];
        long[] rowSums = new long[n];

        for (int i = 0; i < n; i++) {
            long currentSum = 0;
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
                currentSum += matrix[i][j];
            }
            rowSums[i] = currentSum;
        }

        for (int i = 0; i < n; i++) {
            System.out.print(rowSums[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}