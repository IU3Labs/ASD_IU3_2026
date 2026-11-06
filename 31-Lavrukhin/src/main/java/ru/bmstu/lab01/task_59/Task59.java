/*
 * Задача 59. Определитель матрицы 3x3.
 * Вход: матрица 3 x 3 из целых чисел.
 * Выход: определитель матрицы.
 */
package ru.bmstu.lab01.task_59;

import java.math.BigInteger;
import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayUtils;

/**
 * Определитель матрицы 3x3 разложением по первой строке:
 * det = a00 * (a11 * a22 - a12 * a21) - a01 * (a10 * a22 - a12 * a20) + a02 * (a10 * a21 - a11 * a20).
 * Считаем в BigInteger: произведение трёх чисел типа int может не поместиться даже в long.
 */
public class Task59 {

    private static final int SIZE = 3;

    /**
     * Читает 9 чисел (матрицу 3x3 по строкам) и печатает определитель.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix = ArrayUtils.readMatrix(scanner, SIZE, SIZE);
        System.out.println(determinant(matrix));
    }

    /**
     * Считает определитель матрицы 3x3.
     *
     * @param m матрица 3x3
     * @return определитель
     * @throws IllegalArgumentException если матрица не 3x3
     */
    public static BigInteger determinant(int[][] m) {
        ArrayUtils.checkSquare(m);
        if (m.length != SIZE) {
            throw new IllegalArgumentException("Нужна матрица 3x3");
        }
        BigInteger first = big(m[0][0]).multiply(minor(m[1][1], m[1][2], m[2][1], m[2][2]));
        BigInteger second = big(m[0][1]).multiply(minor(m[1][0], m[1][2], m[2][0], m[2][2]));
        BigInteger third = big(m[0][2]).multiply(minor(m[1][0], m[1][1], m[2][0], m[2][1]));
        return first.subtract(second).add(third);
    }

    /** Определитель 2x2 из элементов a b / c d. */
    private static BigInteger minor(int a, int b, int c, int d) {
        return big(a).multiply(big(d)).subtract(big(b).multiply(big(c)));
    }

    private static BigInteger big(int value) {
        return BigInteger.valueOf(value);
    }
}
