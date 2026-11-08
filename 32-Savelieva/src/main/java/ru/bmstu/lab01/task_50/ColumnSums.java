/**
 * Задача 50. Сумма столбцов матрицы.
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из m элементов — сумм столбцов.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_50;

import java.util.Scanner;

/**
 * Суммы столбцов матрицы.
 */
public final class ColumnSums {

    /** Минимально допустимое число строк. */
    private static final int MIN_ROWS = 1;

    /** Максимально допустимое число строк. */
    private static final int MAX_ROWS = 999;

    /** Минимально допустимое число столбцов. */
    private static final int MIN_COLS = 1;

    /** Максимально допустимое число столбцов. */
    private static final int MAX_COLS = 999;

    private ColumnSums() {
    }

    /**
     * Вычисляет суммы столбцов матрицы.
     *
     * <p>В отличие от сумм строк, здесь циклы переставлены: внешний — по строкам,
     * внутренний — по столбцам. Это даёт последовательный доступ к памяти
     * (для двумерного массива в Java строки лежат подряд), что на больших матрицах
     * заметно по кэшу.
     *
     * @param matrix матрица n × m
     * @return массив из m сумм
     * @throws IllegalArgumentException если матрица {@code null}, пустая или размеры вне границ
     */
    public static long[] compute(int[][] matrix) {
        requireValid(matrix);
        int cols = matrix[0].length;
        long[] result = new long[cols];
        for (int[] row : matrix) {
            for (int j = 0; j < cols; j++) {
                result[j] += row[j];
            }
        }
        return result;
    }

    private static void requireValid(int[][] matrix) {
        if (matrix == null || matrix.length < MIN_ROWS || matrix.length > MAX_ROWS) {
            throw new IllegalArgumentException("некорректное число строк");
        }
        int cols = -1;
        for (int[] row : matrix) {
            if (row == null || row.length < MIN_COLS || row.length > MAX_COLS) {
                throw new IllegalArgumentException("некорректная длина строки");
            }
            if (cols == -1) {
                cols = row.length;
            } else if (row.length != cols) {
                throw new IllegalArgumentException("строки разной длины");
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
            for (int j = 0; j < sums.length; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(sums[j]);
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