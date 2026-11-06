package ru.bmstu.lab1.task_53;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 53. Седловые точки.
 *
 * <p>Вход: {@code n m}, затем матрица {@code n x m}. Выход: все элементы, минимальные в своей строке и
 * максимальные в своём столбце, по одному на строке в формате {@code (строка, столбец, значение)}
 * в порядке обхода по строкам. Если седловых точек нет, вывод пуст.
 *
 * <p>Наивная проверка каждого элемента стоила бы O(nm(n + m)). Вместо этого заранее считаются минимумы
 * строк и максимумы столбцов (O(nm)), после чего каждый элемент проверяется за O(1).
 * Итог O(nm) по времени, O(n + m) доп. памяти.
 */
public final class SaddlePoints {

    private SaddlePoints() {
    }

    /**
     * Седловая точка матрицы.
     *
     * @param row    индекс строки
     * @param column индекс столбца
     * @param value  значение элемента
     */
    public record Point(int row, int column, int value) {

        @Override
        public String toString() {
            return "(" + row + ", " + column + ", " + value + ")";
        }
    }

    /**
     * Находит все седловые точки.
     *
     * @param matrix прямоугольная матрица
     * @return седловые точки в порядке обхода по строкам
     */
    public static List<Point> find(int[][] matrix) {
        List<Point> result = new ArrayList<>();
        if (matrix.length == 0) {
            return result;
        }
        int[] rowMin = rowMinimums(matrix);
        int[] columnMax = columnMaximums(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int value = matrix[i][j];
                if (value == rowMin[i] && value == columnMax[j]) {
                    result.add(new Point(i, j, value));
                }
            }
        }
        return result;
    }

    private static int[] rowMinimums(int[][] matrix) {
        int[] result = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = Integer.MAX_VALUE;
            for (int value : matrix[i]) {
                result[i] = Math.min(result[i], value);
            }
        }
        return result;
    }

    private static int[] columnMaximums(int[][] matrix) {
        int[] result = new int[matrix[0].length];
        Arrays.fill(result, Integer.MIN_VALUE);
        for (int[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                result[j] = Math.max(result[j], row[j]);
            }
        }
        return result;
    }

    /**
     * Точка входа: читает матрицу, печатает седловые точки.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int rows = in.nextInt();
            int columns = in.nextInt();
            find(in.nextMatrix(rows, columns)).forEach(out::println);
        });
    }
}
