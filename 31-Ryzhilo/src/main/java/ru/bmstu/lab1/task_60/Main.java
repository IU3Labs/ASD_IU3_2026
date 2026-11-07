package ru.bmstu.lab1.task_60;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 60. Острова из единиц.
 * Выход: количество «островов» — групп связанных единиц (связь по горизонтали и вертикали, не по диагонали).
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
        int[] queue = new int[rows * columns];
        int islands = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (matrix[row][column] == 1) {
                    islands++;
                    flood(matrix, row, column, queue);
                }
            }
        }
        output.println(islands);
    }
    private static void flood(int[][] matrix, int row, int column, int[] queue) {
        int columns = matrix[0].length;
        int head = 0;
        int tail = 0;
        matrix[row][column] = 0;
        queue[tail++] = row * columns + column;
        int[] rowStep = {-1, 1, 0, 0};
        int[] columnStep = {0, 0, -1, 1};
        while (head < tail) {
            int cell = queue[head++];
            for (int direction = 0; direction < rowStep.length; direction++) {
                int nextRow = cell / columns + rowStep[direction];
                int nextColumn = cell % columns + columnStep[direction];
                if (nextRow >= 0 && nextRow < matrix.length && nextColumn >= 0 && nextColumn < columns
                        && matrix[nextRow][nextColumn] == 1) {
                    // Отмечаем при добавлении: клетка попадёт в очередь только один раз.
                    matrix[nextRow][nextColumn] = 0;
                    queue[tail++] = nextRow * columns + nextColumn;
                }
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
