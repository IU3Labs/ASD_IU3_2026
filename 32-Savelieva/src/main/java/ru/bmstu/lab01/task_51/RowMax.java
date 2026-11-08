/**
 * Задача 51. Максимум в каждой строке.
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из n элементов — максимумов строк.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_51;

import java.util.Scanner;

/**
 * Максимум в каждой строке матрицы.
 */
public final class RowMax {

    private static final int MIN_ROWS = 1;
    private static final int MAX_ROWS = 999;
    private static final int MIN_COLS = 1;
    private static final int MAX_COLS = 999;

    private RowMax() {
    }

    /**
     * Возвращает максимум каждой строки.
     *
     * <p>Один проход по каждой строке. В качестве начального значения берём
     * первый элемент строки — это корректно и для строк из отрицательных чисел.
     *
     * @param matrix матрица n × m
     * @return массив из n максимумов
     * @throws IllegalArgumentException если матрица {@code null}, пустая или размеры вне границ
     */
    public static int[] compute(int[][] matrix) {
        requireValid(matrix);
        int[] result = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int max = matrix[i][0];
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
            result[i] = max;
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
            int[] maxes = compute(matrix);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < maxes.length; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(maxes[i]);
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