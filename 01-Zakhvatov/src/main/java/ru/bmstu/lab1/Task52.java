package ru.bmstu.lab1;

/**
 * Задача 52. Минимум в каждом столбце матрицы.
 */
public class Task52 {

    public static int[] colMins(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[] res = new int[m];

        for (int j = 0; j < m; j++) {
            int min = matrix[0][j];
            for (int i = 1; i < n; i++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
            res[j] = min;
        }

        return res;
    }
}
