package ru.bmstu.lab1.task_55;

import java.util.Scanner;

/**
 * Задача 55. Сумма элементов на побочной диагонали.
 */
public class Main {

    /**
     * Читает квадратную матрицу и выводит сумму побочной диагонали.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        System.out.println(calculateSecondaryDiagonalSum(matrix));
    }

    /**
     * Вычисляет сумму элементов побочной диагонали.
     *
     * @param matrix квадратная матрица целых чисел
     * @return сумма элементов побочной диагонали
     */
    public static long calculateSecondaryDiagonalSum(int[][] matrix) {
        long sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][matrix.length - 1 - i];
        }

        return sum;
    }
}