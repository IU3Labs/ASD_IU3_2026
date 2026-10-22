/*
 * Задача 53. Седловые точки.
 * Вход: матрица n x m из целых чисел.
 * Выход: все седловые точки — элементы, одновременно минимальные в своей строке и максимальные
 * в своём столбце. Вывести в формате (индекс_строки, индекс_столбца, значение).
 */
package ru.bmstu.lab01.task_53;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.bmstu.lab01.common.InputReader;
import ru.bmstu.lab01.common.Matrices;

/**
 * Поиск седловых точек матрицы.
 *
 * <p>Формат ввода: {@code n m}, затем матрица построчно. Вывод: каждая седловая точка на отдельной
 * строке в порядке обхода по строкам; если точек нет — {@code -1}.
 * Время O(n * m), дополнительная память O(n + m).
 */
public final class SaddlePointFinder {

    private static final String NOT_FOUND = "-1";

    private SaddlePointFinder() {
    }

    /**
     * Точка входа: читает матрицу из стандартного ввода и печатает седловые точки.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    static void run(InputStream input, PrintStream output) {
        int[][] matrix = new InputReader(input).nextMatrix();
        List<SaddlePoint> points = find(matrix);
        if (points.isEmpty()) {
            output.println(NOT_FOUND);
        }
        points.forEach(output::println);
    }

    /**
     * Находит все седловые точки матрицы.
     *
     * @param matrix прямоугольная матрица
     * @return седловые точки в порядке обхода по строкам (пустой список, если их нет)
     */
    public static List<SaddlePoint> find(int[][] matrix) {
        int cols = Matrices.requireRectangular(matrix);
        List<SaddlePoint> points = new ArrayList<>();
        if (cols == 0) {
            return points;
        }
        // Предпосчёт минимумов строк и максимумов столбцов за один проход вместо проверки
        // каждого элемента по всей строке и столбцу, что дало бы O(n * m * (n + m)).
        int[] rowMin = rowMinimums(matrix);
        int[] colMax = columnMaximums(matrix, cols);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < cols; j++) {
                int value = matrix[i][j];
                if (value == rowMin[i] && value == colMax[j]) {
                    points.add(new SaddlePoint(i, j, value));
                }
            }
        }
        return points;
    }

    private static int[] rowMinimums(int[][] matrix) {
        int[] rowMin = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rowMin[i] = Arrays.stream(matrix[i]).min().orElseThrow();
        }
        return rowMin;
    }

    private static int[] columnMaximums(int[][] matrix, int cols) {
        int[] colMax = new int[cols];
        Arrays.fill(colMax, Integer.MIN_VALUE);
        for (int[] row : matrix) {
            for (int j = 0; j < cols; j++) {
                colMax[j] = Math.max(colMax[j], row[j]);
            }
        }
        return colMax;
    }
}
