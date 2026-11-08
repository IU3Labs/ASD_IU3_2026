/**
 * Задача 54. Сумма элементов на главной диагонали.
 * Вход: квадратная матрица n x n.
 * Выход: сумма элементов на главной диагонали.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_54;

import java.util.Scanner;

/**
 * Сумма главной диагонали квадратной матрицы.
 */
public final class MainDiagonalSum {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 999;

    private MainDiagonalSum() {
    }

    /**
     * Сумма элементов на главной диагонали: {@code matrix[i][i]} для i = 0..n-1.
     *
     * <p>Сумма копится в {@code long}: при n = 10^3 и элементах около
     * {@code Integer.MAX_VALUE} результат достигает 2·10^12 — выходит за {@code int}.
     *
     * @param matrix квадратная матрица
     * @return сумма диагонали
     * @throws IllegalArgumentException если матрица {@code null}, не квадратная или размер вне границ
     */
    public static long compute(int[][] matrix) {
        requireValid(matrix);
        long sum = 0L;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][i];
        }
        return sum;
    }

    private static void requireValid(int[][] matrix) {
        if (matrix == null || matrix.length < MIN_SIZE || matrix.length > MAX_SIZE) {
            throw new IllegalArgumentException("некорректный размер матрицы");
        }
        for (int[] row : matrix) {
            if (row == null || row.length != matrix.length) {
                throw new IllegalArgumentException("матрица должна быть квадратной");
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: ожидалось целое число");
                return;
            }
            int n = scanner.nextInt();
            if (n < MIN_SIZE || n > MAX_SIZE) {
                System.out.println("Ошибка: n вне допустимого диапазона");
                return;
            }
            int[][] matrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!scanner.hasNextInt()) {
                        System.out.println("Ошибка: ожидалось целое число");
                        return;
                    }
                    matrix[i][j] = scanner.nextInt();
                }
            }
            System.out.println(compute(matrix));
        }
    }
}