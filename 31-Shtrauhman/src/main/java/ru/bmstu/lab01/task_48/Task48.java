package ru.bmstu.lab01.task_48;

import java.io.InputStream;
import java.io.PrintStream;
import ru.bmstu.lab01.common.OutputFormat;
import ru.bmstu.lab01.common.TokenReader;

/**
 * Задача 48. Транспонирование матрицы.
 *
 * Вход: число n, затем квадратная матрица n x n. Выход: транспонированная матрица.
 *
 * Транспонирование выполняется на месте: элементы a[i][j] и a[j][i] меняются местами только
 * при j больше i (выше главной диагонали). Так каждая пара обменивается ровно один раз
 * (при обходе всей матрицы они вернулись бы на свои места).
 */
public final class Task48 {

    private Task48() {
    }

    public static void transposeInPlace(int[][] matrix) {
        requireSquare(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                int buffer = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = buffer;
            }
        }
    }

    private static void requireSquare(int[][] matrix) {
        for (int[] row : matrix) {
            if (row.length != matrix.length) {
                throw new IllegalArgumentException("Matrix must be square");
            }
        }
    }

    public static void run(InputStream in, PrintStream out) {
        TokenReader reader = new TokenReader(in);
        int size = reader.nextSize();
        int[][] matrix = reader.nextIntMatrix(size, size);
        transposeInPlace(matrix);
        for (int[] row : matrix) {
            out.println(OutputFormat.join(row));
        }
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
