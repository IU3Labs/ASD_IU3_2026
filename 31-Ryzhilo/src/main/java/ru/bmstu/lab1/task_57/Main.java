package ru.bmstu.lab1.task_57;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 57. Спиральный обход матрицы.
 * Выход: элементы матрицы, обойдённые по спирали от левого верхнего угла по часовой стрелке.
 * Сложность по времени — O(nm).
 * Память — O(nm).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int rows = input.nextInt();
        int columns = input.nextInt();
        int[][] matrix = readMatrix(input, rows, columns);
        printArray(output, spiral(matrix));
    }
    private static int[] spiral(int[][] matrix) {
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        int[] result = new int[matrix.length * matrix[0].length];
        int position = 0;
        while (top <= bottom && left <= right) {
            for (int column = left; column <= right; column++) {
                result[position++] = matrix[top][column];
            }
            top++;
            for (int row = top; row <= bottom; row++) {
                result[position++] = matrix[row][right];
            }
            right--;
            if (top <= bottom) {
                for (int column = right; column >= left; column--) {
                    result[position++] = matrix[bottom][column];
                }
                bottom--;
            }
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result[position++] = matrix[row][left];
                }
                left++;
            }
        }
        return result;
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

    /** Выводит элементы массива через пробел. */
    private static void printArray(PrintStream output, int[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                output.print(' ');
            }
            output.print(values[i]);
        }
        output.println();
    }
}
