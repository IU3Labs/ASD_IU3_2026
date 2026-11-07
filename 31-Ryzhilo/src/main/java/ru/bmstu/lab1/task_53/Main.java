package ru.bmstu.lab1.task_53;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 53. Седловые точки.
 * Выход: все седловые точки — элементы, одновременно минимальные в своей строке и максимальные в своём
 * столбце. Вывести в формате (индекс_строки, индекс_столбца, значение).
 * Сложность по времени — O(nm).
 * Память — O(nm + n + m).
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
        int[] rowMinimums = new int[rows];
        int[] columnMaximums = new int[columns];
        findExtremes(matrix, rowMinimums, columnMaximums);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int value = matrix[row][column];
                if (value == rowMinimums[row] && value == columnMaximums[column]) {
                    output.println("(" + row + ", " + column + ", " + value + ")");
                }
            }
        }
    }
    private static void findExtremes(int[][] matrix, int[] rowMinimums, int[] columnMaximums) {
        for (int column = 0; column < columnMaximums.length; column++) {
            columnMaximums[column] = Integer.MIN_VALUE;
        }
        for (int row = 0; row < matrix.length; row++) {
            rowMinimums[row] = Integer.MAX_VALUE;
            for (int column = 0; column < matrix[row].length; column++) {
                rowMinimums[row] = Math.min(rowMinimums[row], matrix[row][column]);
                columnMaximums[column] = Math.max(columnMaximums[column], matrix[row][column]);
            }
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
