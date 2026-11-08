/**
 * Задача 53. Седловые точки.
 * Вход: матрица n x m из целых чисел.
 * Выход: все седловые точки (минимальные в строке и максимальные в столбце)
 * в формате (строка, столбец, значение).
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_53;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Поиск седловых точек матрицы.
 */
public final class SaddlePoints {

    private static final int MIN_ROWS = 1;
    private static final int MAX_ROWS = 999;
    private static final int MIN_COLS = 1;
    private static final int MAX_COLS = 999;

    private SaddlePoints() {
    }

    /**
     * Находит все седловые точки — элементы, одновременно минимальные в своей
     * строке и максимальные в своём столбце.
     *
     * <p>Сначала за один проход вычисляются два массива: {@code rowMin[i]} —
     * минимум строки i, {@code colMax[j]} — максимум столбца j. Затем каждый
     * элемент проверяется за O(1): {@code matrix[i][j] == rowMin[i] && matrix[i][j] == colMax[j]}.
     * Без предвычисления проверка каждого элемента стоила бы O(n + m), итого O(n·m·(n+m)).
     * Здесь — O(n·m).
     *
     * <p>Сравнение на равенство (а не {@code <=}) корректно находит и все точки
     * при наличии одинаковых значений.
     *
     * @param matrix матрица n × m
     * @return список троек {row, col, value}
     * @throws IllegalArgumentException если матрица {@code null}, пустая, размеры вне границ
     *                                  или строки разной длины
     */
    public static List<int[]> find(int[][] matrix) {
        requireValid(matrix);
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[] rowMin = new int[rows];
        int[] colMax = new int[cols];

        for (int i = 0; i < rows; i++) {
            rowMin[i] = matrix[i][0];
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] < rowMin[i]) {
                    rowMin[i] = matrix[i][j];
                }
            }
        }
        for (int j = 0; j < cols; j++) {
            colMax[j] = matrix[0][j];
            for (int i = 1; i < rows; i++) {
                if (matrix[i][j] > colMax[j]) {
                    colMax[j] = matrix[i][j];
                }
            }
        }

        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == rowMin[i] && matrix[i][j] == colMax[j]) {
                    result.add(new int[] {i, j, matrix[i][j]});
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
            List<int[]> points = find(matrix);
            if (points.isEmpty()) {
                System.out.println("Седловых точек нет");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int[] p : points) {
                if (sb.length() > 0) {
                    sb.append('\n');
                }
                sb.append(p[0]).append(' ').append(p[1]).append(' ').append(p[2]);
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