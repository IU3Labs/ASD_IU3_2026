package ru.bmstu.lab01.task_58;

import java.io.InputStream;
import java.io.PrintStream;
import ru.bmstu.lab01.common.OutputFormat;
import ru.bmstu.lab01.common.TokenReader;

/**
 * Задача 58. Заполнение матрицы змейкой.
 *
 * Вход: числа n и m. Выход: матрица n x m, заполненная числами от 1 до n * m по строкам:
 * чётные строки (с нуля) слева направо, нечётные - справа налево.
 *
 * Значение ячейки считается по формуле через чётность строки:
 * a[i][j] = i * m + (i чётна ? j : m - 1 - j) + 1.
 */
public final class Task58 {

    private Task58() {
    }

    public static int[][] snake(int rows, int cols) {
        Math.multiplyExact(rows, cols);
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            boolean leftToRight = i % 2 == 0;
            for (int j = 0; j < cols; j++) {
                int offset = leftToRight ? j : cols - 1 - j;
                matrix[i][j] = i * cols + offset + 1;
            }
        }
        return matrix;
    }

    public static void run(InputStream in, PrintStream out) {
        TokenReader reader = new TokenReader(in);
        int rows = reader.nextSize();
        int cols = reader.nextSize();
        for (int[] row : snake(rows, cols)) {
            out.println(OutputFormat.join(row));
        }
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
