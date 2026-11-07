package ru.bmstu.lab1.task_59;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Задача 59. Определитель матрицы 3×3.
 * Выход: определитель матрицы.
 * Сложность по времени — O(1) для матрицы 3×3.
 * Память — O(1).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        BigInteger[][] matrix = new BigInteger[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                matrix[row][column] = BigInteger.valueOf(input.nextInt());
            }
        }
        BigInteger determinant = BigInteger.ZERO;
        // Разложение по первой строке: три алгебраических дополнения.
        for (int column = 0; column < 3; column++) {
            int next = (column + 1) % 3;
            int last = (column + 2) % 3;
            BigInteger minor = matrix[1][next].multiply(matrix[2][last])
                    .subtract(matrix[1][last].multiply(matrix[2][next]));
            determinant = determinant.add(matrix[0][column].multiply(minor));
        }
        output.println(determinant);
    }

}
