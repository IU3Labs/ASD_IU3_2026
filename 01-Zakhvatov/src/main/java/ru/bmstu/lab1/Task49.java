package ru.bmstu.lab1;

/**
 * Задача 49. Сумма строк матрицы.
 */
public class Task49 {

    public static int[] rowSums(int[][] matrix) {
        int n = matrix.length;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            int s = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                s += matrix[i][j];
            }
            res[i] = s;
        }

        return res;
    }
}
