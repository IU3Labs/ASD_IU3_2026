package ru.bmstu.lab1.task_49;

import java.util.Scanner;

/**
 * Задача 49. Вычисление сумм строк матрицы.
 */
public class Main {

    /**
     * Читает размеры и элементы матрицы, выводит суммы строк.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int[][] matrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        long[] sums = calculateRowSums(matrix);
        for (int i = 0; i < sums.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(sums[i]);
        }
        System.out.println();
    }

    /**
     * Вычисляет сумму элементов каждой строки.
     *
     * @param matrix исходная матрица целых чисел
     * @return массив сумм строк
     */
    public static long[] calculateRowSums(int[][] matrix) {
        long[] sums = new long[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i] += matrix[i][j];
            }
        }

        return sums;
    }
}