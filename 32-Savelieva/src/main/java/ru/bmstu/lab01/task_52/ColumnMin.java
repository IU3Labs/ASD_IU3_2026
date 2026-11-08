/**
 * Задача 52. Минимум в каждом столбце.
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из m элементов — минимумов столбцов.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_52;

import java.util.Scanner;

/**
 * Минимум в каждом столбце матрицы.
 */
public final class ColumnMin {

    private static final int MIN_ROWS = 1;
    private static final int MAX_ROWS = 999;
    private static final int MIN_COLS = 1;
    private static final int MAX_COLS = 999;

    private ColumnMin() {
    }

    /**
     * Возвращает минимум каждого столбца.
     *
     * <p>Инициализируем каждый столбец первым элементом, затем обновляем.
     * Начальное значение {@code Integer.MAX_VALUE} не используется, чтобы
     * не было проблем с матрицами из одинаковых чисел.
     *
     * @param matrix матрица n × m
     * @return массив из m минимумов
     * @throws IllegalArgumentException если матрица {@code null}, пустая, размеры вне границ
     *                                  или строки разной длины
     */
    public static int[] compute(int[][] matrix) {
        requireValid(matrix);
        int cols = matrix[0].length;
        int[] result = new int[cols];
        for (int j = 0; j < cols; j++) {
            result[j] = matrix[0][j];
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] < result[j]) {
                    result[j] = matrix[i][j];
                }
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
            int[] mins = compute(matrix);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < mins.length; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(mins[j]);
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