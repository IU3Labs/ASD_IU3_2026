/*
 * Задача 55. Сумма элементов на побочной диагонали.
 * Вход: квадратная матрица n x n.
 * Выход: сумма элементов на побочной диагонали (от правого верхнего угла к левому нижнему).
 */
package ru.bmstu.lab01.task_55;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayUtils;

/**
 * Сумма элементов побочной диагонали. В строке i на побочной диагонали стоит элемент
 * из столбца n - 1 - i.
 */
public class Task55 {

    /**
     * Читает n, затем матрицу n x n и печатает сумму побочной диагонали.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = ArrayUtils.readSize(scanner);
        int[][] matrix = ArrayUtils.readMatrix(scanner, n, n);
        System.out.println(sumOfSecondDiagonal(matrix));
    }

    /**
     * Считает сумму элементов побочной диагонали.
     *
     * @param matrix квадратная матрица
     * @return сумма (в long, чтобы не было переполнения)
     * @throws IllegalArgumentException если матрица пустая или не квадратная
     */
    public static long sumOfSecondDiagonal(int[][] matrix) {
        ArrayUtils.checkSquare(matrix);
        int n = matrix.length;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += matrix[i][n - 1 - i];
        }
        return sum;
    }
}
