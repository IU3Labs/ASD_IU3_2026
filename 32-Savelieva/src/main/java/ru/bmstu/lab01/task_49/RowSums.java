/**
 * Задача 49. Сумма строк матрицы.
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из n элементов — сумм строк.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_49;

import java.util.Scanner;

/**
 * Суммы строк матрицы.
 */
public final class RowSums {

    /** Минимально допустимое число строк. */
    private static final int MIN_ROWS = 1;

    /** Максимально допустимое число строк. */
    private static final int MAX_ROWS = 999;

    /** Минимально допустимое число столбцов. */
    private static final int MIN_COLS = 1;

    /** Максимально допустимое число столбцов. */
    private static final int MAX_COLS = 999;

    private RowSums() {
    }

    /**
     * Вычисляет суммы строк матрицы.
     *
     * <p>Суммы накапливаются в {@code long}: при m до 10^3 и элементах около
     * {@code Integer.MAX_VALUE} одна строка даёт до 2·10^12 — выходит за {@code int}.
     *
     * @param matrix матрица n × m
     * @return массив из n сумм
     * @throws IllegalArgumentException если матрица {@code null}, пустая или размеры вне границ
     */
    public static long[] compute(int[][] matrix) {
        requireValid(matrix);
        long[] result = new long[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            long sum = 0L;
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
            }
            result[i] = sum;
        }
        return result;
    }

    private static void requireValid(int[][] matrix) {
        if (matrix == null || matrix.length < MIN_ROWS || matrix.length > MAX_ROWS) {
            throw new IllegalArgumentException("некорректное число строк");
        }
        for (int[] row : matrix) {
            if (row == null || row.length < MIN_COLS || row.length > MAX_COLS) {
                throw new IllegalArgumentException("некорректная длина строки");
            }
        }
    }

    /**
     * Точка входа. Формат ввода: n, m, затем n·m чисел (по строкам).
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = readInt(scanner, "строк");
            if (n < 0) {
                return;
            }
            int m = readInt(scanner, "столбцов");
            if (m < 0) {
                return;
            }
            int[][] matrix = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (!scanner.hasNextInt()) {
                        System.out.println("Ошибка: ожидалось целое число");
                        return;
                    }
                    matrix[i][j] = scanner.nextInt();
                }
            }
            long[] sums = compute(matrix);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sums.length; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(sums[i]);
            }
            System.out.println(sb);
        }
    }

    private static int readInt(Scanner scanner, String what) {
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: ожидалось целое число");
            return -1;
        }
        int value = scanner.nextInt();
        if (value < 1 || value > 999) {
            System.out.println("Ошибка: " + what + " вне допустимого диапазона");
            return -1;
        }
        return value;
    }
}