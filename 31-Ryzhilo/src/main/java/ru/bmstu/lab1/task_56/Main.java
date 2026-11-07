package ru.bmstu.lab1.task_56;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Задача 56. Умножение матриц.
 * Выход: матрица C = A x B размера n x k.
 * Сложность по времени — O(nmk) арифметических операций.
 * Память — O(nm + mk) для входных матриц.
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int rows = input.nextInt();
        int shared = input.nextInt();
        int columns = input.nextInt();
        int[][] first = readMatrix(input, rows, shared);
        int[][] second = readMatrix(input, shared, columns);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                BigInteger sum = BigInteger.ZERO;
                for (int i = 0; i < shared; i++) {
                    long term = (long) first[row][i] * second[i][column];
                    sum = sum.add(BigInteger.valueOf(term));
                }
                if (column > 0) {
                    output.print(' ');
                }
                output.print(sum);
            }
            output.println();
        }
    }

    /** Читает матрицу заданного размера по строкам. */
    private static int[][] readMatrix(Scanner input, int rows, int columns) {
        int[][] matrix = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                matrix[row][column] = input.nextInt();
            }
        }
        return matrix;
    }
}
