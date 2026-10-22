package ru.bmstu.lab1.common;

import java.util.Scanner;

/**
 * Чтение массивов и матриц из входного потока.
 *
 * <p>Формат массива: сначала размер {@code n}, затем {@code n} целых чисел.
 * Формат матрицы: сначала {@code n} и {@code m}, затем {@code n * m} чисел построчно.
 * Вынесено в общий класс, чтобы не дублировать разбор ввода в каждой задаче.
 */
public final class InputReader {

    private InputReader() {
    }

    /**
     * Читает размер массива и затем сами элементы.
     *
     * @param in источник данных
     * @return прочитанный массив
     */
    public static int[] readArray(Scanner in) {
        int size = in.nextInt();
        return readElements(in, size);
    }

    /**
     * Читает заданное количество целых чисел.
     *
     * @param in    источник данных
     * @param count сколько чисел прочитать
     * @return прочитанный массив
     * @throws IllegalArgumentException если {@code count} отрицательный
     */
    public static int[] readElements(Scanner in, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Размер массива не может быть отрицательным: " + count);
        }
        int[] array = new int[count];
        for (int i = 0; i < count; i++) {
            array[i] = in.nextInt();
        }
        return array;
    }

    /**
     * Читает размеры матрицы и её элементы построчно.
     *
     * @param in источник данных
     * @return прочитанная матрица
     */
    public static int[][] readMatrix(Scanner in) {
        int rows = in.nextInt();
        int cols = in.nextInt();
        if (rows < 0 || cols < 0) {
            throw new IllegalArgumentException("Размеры матрицы не могут быть отрицательными");
        }
        int[][] matrix = new int[rows][];
        for (int row = 0; row < rows; row++) {
            matrix[row] = readElements(in, cols);
        }
        return matrix;
    }
}
