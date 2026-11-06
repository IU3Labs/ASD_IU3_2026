/*
 * Задача №54. Сумма элементов на главной диагонали.
 * Вычислить сумму элементов главной диагонали квадратной матрицы.
 */

package ru.bmstu.lab1.task_54;

import ru.bmstu.lab1.InputReader;

/**
 * Решение задачи №54: «Сумма элементов на главной диагонали».
 */
public final class Task54 {
    private Task54() {
    }

    /** Суммирует элементы главной диагонали; для суммы использует long. */
    public static long diagonalSum(int[][] matrix) {
        long result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[i][i];
        }
        return result;
    }

    /** Считывает квадратную матрицу и выводит сумму главной диагонали. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int size = input.nextInt();
        System.out.println(diagonalSum(input.nextMatrix(size, size)));
    }
}
