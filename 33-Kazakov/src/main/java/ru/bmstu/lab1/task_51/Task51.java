package ru.bmstu.lab1.task_51;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.MatrixInput;
/**
 * Задача 51 — найти максимальный элемент каждой строки матрицы
 */
public class Task51 {

    /**
     * Возвращает максимумы строк непустой прямоугольной матрицы
     */
    public static int[] maxRows(int[][] matrix) {
        int[] maximums = new int[matrix.length];

        for (int row = 0; row < matrix.length; row++) {
            int maximum = matrix[row][0];

            for (int column = 1; column < matrix[row].length; column++) {
                if (matrix[row][column] > maximum) {
                    maximum = matrix[row][column];
                }
            }

            maximums[row] = maximum;
        }

        return maximums;
    }

    /**
     * Считывает матрицу и выводит максимумы строк
     */
    public static void solve(Scanner input, PrintStream output) {
        int[][] matrix = MatrixInput.readMatrix(input);

        for (int maximum : maxRows(matrix)) {
            output.println(maximum);
        }
    }

    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }
}