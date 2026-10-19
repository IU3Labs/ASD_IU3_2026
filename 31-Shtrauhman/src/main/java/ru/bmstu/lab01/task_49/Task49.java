package ru.bmstu.lab01.task_49;

import java.io.InputStream;
import java.io.PrintStream;
import ru.bmstu.lab01.common.OutputFormat;
import ru.bmstu.lab01.common.TokenReader;

/**
 * Задача 49. Сумма строк матрицы.
 *
 * Вход: числа n и m, затем n строк по m целых чисел. Выход: n чисел через пробел - суммы строк.
 * Суммы хранятся в long, чтобы строка из больших int не переполнялась.
 */
public final class Task49 {

    private Task49() {
    }

    public static long[] rowSums(int[][] matrix) {
        long[] sums = new long[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int value : matrix[i]) {
                sums[i] += value;
            }
        }
        return sums;
    }

    public static void run(InputStream in, PrintStream out) {
        TokenReader reader = new TokenReader(in);
        int rows = reader.nextSize();
        int cols = reader.nextSize();
        out.println(OutputFormat.join(rowSums(reader.nextIntMatrix(rows, cols))));
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
