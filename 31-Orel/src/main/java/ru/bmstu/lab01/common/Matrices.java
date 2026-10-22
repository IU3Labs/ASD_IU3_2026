package ru.bmstu.lab01.common;

/**
 * Общие проверки для задач с матрицами.
 */
public final class Matrices {

    private Matrices() {
    }

    /**
     * Проверяет, что все строки матрицы одной длины, и возвращает число столбцов.
     *
     * @param matrix матрица (может не иметь строк)
     * @return число столбцов; 0, если строк нет
     * @throws IllegalArgumentException если строки разной длины
     */
    public static int requireRectangular(int[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int cols = matrix[0].length;
        for (int[] row : matrix) {
            if (row.length != cols) {
                throw new IllegalArgumentException("All matrix rows must have the same length");
            }
        }
        return cols;
    }
}
