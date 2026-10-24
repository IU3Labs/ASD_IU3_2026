package ru.bmstu.lab1;

/**
 * Решение задачи 59: определитель матрицы 3x3.
 */
public class Task59 {

    /**
     * Вычисляет определитель матрицы 3x3.
     * Возвращает long, так как результат умножения трех int может переполнить int.
     *
     * @param matrix матрица 3x3 целых чисел
     * @return определитель матрицы
     */
    public static long determinant3x3(int[][] matrix) {
        long a = matrix[0][0], b = matrix[0][1], c = matrix[0][2];
        long d = matrix[1][0], e = matrix[1][1], f = matrix[1][2];
        long g = matrix[2][0], h = matrix[2][1], i = matrix[2][2];

        return a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
    }
}