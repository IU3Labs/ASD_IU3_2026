package ru.bmstu.lab1;

/**
 * Задача 51. Максимум в каждой строке матрицы.
 */
public class Task51 {

    public static int[] rowMaxes(int[][] matrix) {
        int n = matrix.length;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            int max = matrix[i][0];
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
            res[i] = max;
        }

        return res;
    }
}
