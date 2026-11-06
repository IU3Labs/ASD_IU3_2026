/*
 * Задача №59. Определитель матрицы 3×3.
 * Вычислить определитель целочисленной матрицы размером 3×3.
 */

package ru.bmstu.lab1.task_59;

import java.math.BigInteger;
import ru.bmstu.lab1.InputReader;

/**
 * Решение задачи №59: «Определитель матрицы 3×3».
 */
public final class Task59 {
    private Task59() {
    }

    /** Точно вычисляет определитель, включая большие значения элементов. */
    public static BigInteger determinant(int[][] matrix) {
        // Разложение по первой строке: a(ei - fh) - b(di - fg) + c(dh - eg).
        BigInteger first = minor(matrix[1][1], matrix[2][2], matrix[1][2], matrix[2][1]);
        BigInteger second = minor(matrix[1][0], matrix[2][2], matrix[1][2], matrix[2][0]);
        BigInteger third = minor(matrix[1][0], matrix[2][1], matrix[1][1], matrix[2][0]);
        return BigInteger.valueOf(matrix[0][0]).multiply(first)
                .subtract(BigInteger.valueOf(matrix[0][1]).multiply(second))
                .add(BigInteger.valueOf(matrix[0][2]).multiply(third));
    }

    private static BigInteger minor(int a, int b, int c, int d) {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b))
                .subtract(BigInteger.valueOf(c).multiply(BigInteger.valueOf(d)));
    }

    /** Считывает девять элементов матрицы 3×3 и выводит определитель. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        System.out.println(determinant(input.nextMatrix(3, 3)));
    }
}
