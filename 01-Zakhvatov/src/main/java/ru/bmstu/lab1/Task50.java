package ru.bmstu.lab1;

/**
 * Задача 50. Сумма столбцов матрицы.
 */
public class Task50 {

    public static int[] colSums(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[] res = new int[m];

        for (int j = 0; j < m; j++) {
            int s = 0;
            for (int i = 0; i < n; i++) {
                s += matrix[i][j];
            }
            res[j] = s;
        }

        return res;
    }
}
