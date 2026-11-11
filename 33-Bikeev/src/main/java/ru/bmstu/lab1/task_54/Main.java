package ru.bmstu.lab1.task_54;

import java.util.Scanner;

/**
 * Задача 54. Сумма элементов на главной диагонали.
 */
public class Main {

    /**
     * Читает квадратную матрицу и выводит сумму главной диагонали.
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

        System.out.println(calculateMainDiagonalSum(matrix));
    }

    /**
     * Вычисляет сумму элементов главной диагонали.
     *
     * @param matrix квадратная матрица целых чисел
     * @return сумма элементов главной диагонали
     */
    public static long calculateMainDiagonalSum(int[][] matrix) {
        long sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][i];
        }

        return sum;
    }
}