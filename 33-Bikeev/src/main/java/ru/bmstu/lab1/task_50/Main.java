package ru.bmstu.lab1.task_50;

import java.util.Scanner;

/**
 * Задача 50. Вычисление сумм столбцов матрицы.
 */
public class Main {

    /**
     * Читает матрицу и выводит суммы её столбцов.
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

        long[] sums = calculateColumnSums(matrix, columns);
        for (int j = 0; j < sums.length; j++) {
            if (j > 0) {
                System.out.print(" ");
            }
            System.out.print(sums[j]);
        }
        System.out.println();
    }

    /**
     * Вычисляет сумму элементов каждого столбца.
     *
     * @param matrix прямоугольная матрица целых чисел
     * @param columns количество столбцов матрицы
     * @return массив сумм столбцов
     */
    public static long[] calculateColumnSums(int[][] matrix, int columns) {
        long[] sums = new long[columns];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < columns; j++) {
                sums[j] += matrix[i][j];
            }
        }

        return sums;
    }
}