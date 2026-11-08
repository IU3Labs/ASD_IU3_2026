package ru.bmstu.lab1.common;

/**
 * Общие проверки входных данных, используемые несколькими задачами.
 */
public final class Validation {

    private Validation() {
    }

    /**
     * Проверяет, что массив существует и содержит хотя бы один элемент.
     *
     * @param array проверяемый массив
     * @throws IllegalArgumentException если массив {@code null} или пустой
     */
    public static void requireNonEmpty(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }
    }

    /**
     * Проверяет, что матрица непустая и прямоугольная (все строки одной длины, длина больше нуля).
     *
     * @param matrix проверяемая матрица
     * @throws IllegalArgumentException если матрица пустая, содержит {@code null}-строку или строки разной длины
     */
    public static void requireRectangular(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException("Матрица не должна быть пустой");
        }
        int cols = matrix[0].length;
        for (int[] row : matrix) {
            if (row == null || row.length != cols) {
                throw new IllegalArgumentException("Все строки матрицы должны быть одной длины");
            }
        }
    }
}
