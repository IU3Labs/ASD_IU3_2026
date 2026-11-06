/*
 * Задача 47. Поворот массива на 90 градусов.
 * Вход: двумерный массив n x m.
 * Выход: массив m x n, полученный поворотом на 90 градусов по часовой стрелке.
 */
package ru.bmstu.lab01.task_47;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayUtils;

/**
 * Поворот матрицы на 90 градусов по часовой стрелке.
 * При повороте элемент из строки i и столбца j попадает в строку j и столбец n - 1 - i:
 * первая строка становится последним столбцом, последняя строка — первым столбцом.
 */
public class Task47 {

    /**
     * Читает n и m, затем матрицу n x m и печатает повёрнутую матрицу m x n.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = ArrayUtils.readSize(scanner);
        int m = ArrayUtils.readSize(scanner);
        int[][] matrix = ArrayUtils.readMatrix(scanner, n, m);
        for (int[] row : rotate(matrix)) {
            System.out.println(ArrayUtils.toLine(row));
        }
    }

    /**
     * Поворачивает матрицу на 90 градусов по часовой стрелке. Исходная матрица не меняется.
     *
     * @param matrix матрица n x m
     * @return новая матрица m x n
     * @throws IllegalArgumentException если матрица пустая или строки разной длины
     */
    public static int[][] rotate(int[][] matrix) {
        ArrayUtils.checkMatrix(matrix);
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] rotated = new int[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }
}
