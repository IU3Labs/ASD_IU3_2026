package ru.bmstu.lab01.util;

import java.util.Scanner;

/**
 * Вспомогательные методы для чтения и печати массивов и матриц.
 * Вынесены отдельно, чтобы не повторять один и тот же код в каждой задаче.
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * Читает массив: сначала его длину n, потом n чисел.
     *
     * @param scanner откуда читать
     * @return прочитанный массив
     * @throws IllegalArgumentException если длина отрицательная
     */
    public static int[] readArray(Scanner scanner) {
        int n = readSize(scanner);
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    /**
     * Читает матрицу rows x cols по строкам.
     *
     * @param scanner откуда читать
     * @param rows    количество строк
     * @param cols    количество столбцов
     * @return прочитанная матрица
     */
    public static int[][] readMatrix(Scanner scanner, int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    /**
     * Читает размер (длину массива, число строк или столбцов).
     *
     * @param scanner откуда читать
     * @return размер
     * @throws IllegalArgumentException если размер отрицательный
     */
    public static int readSize(Scanner scanner) {
        int size = scanner.nextInt();
        if (size < 0) {
            throw new IllegalArgumentException("Размер не может быть отрицательным: " + size);
        }
        return size;
    }

    /**
     * Переводит массив в строку, где числа разделены пробелами.
     *
     * @param array массив
     * @return строка, например "1 2 3"
     */
    public static String toLine(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * Проверяет, что матрица не пустая и все её строки одной длины.
     *
     * @param matrix матрица
     * @throws IllegalArgumentException если это не так
     */
    public static void checkMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException("Матрица пустая");
        }
        for (int[] row : matrix) {
            if (row == null || row.length != matrix[0].length) {
                throw new IllegalArgumentException("Строки матрицы разной длины");
            }
        }
    }

    /**
     * Проверяет, что матрица квадратная.
     *
     * @param matrix матрица
     * @throws IllegalArgumentException если матрица пустая или не квадратная
     */
    public static void checkSquare(int[][] matrix) {
        checkMatrix(matrix);
        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }
    }
}
