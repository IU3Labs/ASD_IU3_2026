/*
 * Задача 54. Сумма элементов на главной диагонали.
 * Вход: квадратная матрица n x n.
 * Выход: сумма элементов на главной диагонали.
 */
package ru.bmstu.lab01.task_54;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayUtils;

/**
 * Сумма элементов главной диагонали (элементы a[i][i]).
 */
public class Task54 {

    /**
     * Читает n, затем матрицу n x n и печатает сумму главной диагонали.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = ArrayUtils.readSize(scanner);
        int[][] matrix = ArrayUtils.readMatrix(scanner, n, n);
        System.out.println(sumOfMainDiagonal(matrix));
    }

    /**
     * Считает сумму элементов главной диагонали.
     *
     * @param matrix квадратная матрица
     * @return сумма (в long, чтобы не было переполнения)
     * @throws IllegalArgumentException если матрица пустая или не квадратная
     */
    public static long sumOfMainDiagonal(int[][] matrix) {
        ArrayUtils.checkSquare(matrix);
        long sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][i];
        }
        return sum;
    }
}
