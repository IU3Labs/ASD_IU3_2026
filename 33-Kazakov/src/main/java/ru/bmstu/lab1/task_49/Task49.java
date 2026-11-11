package ru.bmstu.lab1.task_49;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.MatrixInput;
/**
 * Задача 49. Вычислить сумму элементов каждой строки целочисленной матрицы
 */
public class Task49 {

    /**
     * Возвращает массив сумм строк исходной матрицы
     */
    public static long[] sumRows(int[][] matrix) {
        long[] sums = new long[matrix.length];

        for (int row = 0; row < matrix.length; row++) {
            for (int value : matrix[row]) {
                sums[row] += value;
            }
        }

        return sums;
    }

    /**
     * Считывает размеры и элементы матрицы, затем выводит суммы строк
     */
    public static void solve(Scanner input, PrintStream output) {
        int[][] matrix = MatrixInput.readMatrix(input);

        for (long sum : sumRows(matrix)) {
            output.println(sum);
        }
    }

    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }
}