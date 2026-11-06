/*
 * Задача 52. Минимум в каждом столбце.
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из m элементов — минимальных значений в каждом столбце.
 */
package ru.bmstu.lab01.task_52;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayUtils;

/**
 * Поиск минимума в каждом столбце матрицы.
 */
public class Task52 {

    /**
     * Читает n и m, затем матрицу n x m и печатает минимумы столбцов.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = ArrayUtils.readSize(scanner);
        int m = ArrayUtils.readSize(scanner);
        int[][] matrix = ArrayUtils.readMatrix(scanner, n, m);
        System.out.println(ArrayUtils.toLine(columnMinimums(matrix)));
    }

    /**
     * Находит минимальный элемент в каждом столбце.
     *
     * @param matrix матрица n x m
     * @return массив из m минимумов, j-й элемент — минимум j-го столбца
     * @throws IllegalArgumentException если матрица пустая или строки разной длины
     */
    public static int[] columnMinimums(int[][] matrix) {
        ArrayUtils.checkMatrix(matrix);
        int m = matrix[0].length;
        int[] minimums = new int[m];
        for (int j = 0; j < m; j++) {
            // за начальное значение берём первый элемент столбца, а не 0 или Integer.MAX_VALUE
            int min = matrix[0][j];
            for (int i = 1; i < matrix.length; i++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
            minimums[j] = min;
        }
        return minimums;
    }
}
