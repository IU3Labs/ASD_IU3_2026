package ru.bmstu.lab01.task_49;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Задача 49. Сумма строк матрицы.
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из n элементов, суммы строк матрицы.
 */
public class Task49 {

    /**
     * Вычисляет сумму элементов каждой строки матрицы.
     *
     * @param matrix матрица n x m
     * @return массив из n элементов, где i-й элемент равен сумме i-й строки
     */
    public static long[] rowSums(int[][] matrix) {
        long[] sums = new long[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i] += matrix[i][j];
            }
        }
        return sums;
    }

    /**
     * Читает с клавиатуры числа n и m, затем матрицу n x m построчно
     * и выводит массив сумм строк.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        System.out.println(Arrays.toString(rowSums(matrix)));
    }
}