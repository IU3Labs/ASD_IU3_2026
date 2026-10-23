package ru.bmstu.lab01.task_54;

import java.util.Scanner;

/**
 * Задача 54. Сумма элементов на главной диагонали.
 * Вход: квадратная матрица n x n.
 * Выход: сумма элементов на главной диагонали.
 */
public class Task54 {

    /**
     * Вычисляет сумму элементов главной диагонали квадратной матрицы.
     * У элементов главной диагонали номер строки совпадает с номером столбца,
     * поэтому достаточно одного прохода по индексу i.
     *
     * @param matrix квадратная матрица n x n
     * @return сумма элементов matrix[i][i]
     * @throws IllegalArgumentException если матрица не квадратная
     */
    public static long mainDiagonalSum(int[][] matrix) {
        long sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != matrix.length) {
                throw new IllegalArgumentException("Matrix must be square");
            }
            sum += matrix[i][i];
        }
        return sum;
    }

    /**
     * Читает с клавиатуры число n, затем матрицу n x n построчно
     * и выводит сумму элементов главной диагонали.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        System.out.println(mainDiagonalSum(matrix));
    }
}