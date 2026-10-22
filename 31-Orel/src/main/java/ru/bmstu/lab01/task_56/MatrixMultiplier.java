/*
 * Задача 56. Умножение матриц.
 * Вход: матрица A размера n x m и матрица B размера m x k.
 * Выход: матрица C = A x B размера n x k.
 * Требование: O(n x m x k) — по времени, O(n x k) — по памяти.
 */
package ru.bmstu.lab01.task_56;

import java.io.InputStream;
import java.io.PrintStream;

import ru.bmstu.lab01.common.InputReader;
import ru.bmstu.lab01.common.Matrices;
import ru.bmstu.lab01.common.OutputFormatter;

/**
 * Умножение матриц классическим алгоритмом.
 *
 * <p>Формат ввода: {@code n m k}, затем матрица A (n x m) и матрица B (m x k) построчно.
 * Вывод: матрица C (n x k) построчно. Время O(n * m * k), дополнительная память O(n * k)
 * (только под результат).
 */
public final class MatrixMultiplier {

    private MatrixMultiplier() {
    }

    /**
     * Точка входа: читает две матрицы из стандартного ввода и печатает их произведение.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    static void run(InputStream input, PrintStream output) {
        InputReader reader = new InputReader(input);
        int rows = reader.nextSize();
        int inner = reader.nextSize();
        int cols = reader.nextSize();
        int[][] left = reader.nextMatrix(rows, inner);
        int[][] right = reader.nextMatrix(inner, cols);
        output.println(OutputFormatter.join(multiply(left, right)));
    }

    /**
     * Перемножает матрицы.
     *
     * @param left  матрица A размера n x m
     * @param right матрица B размера m x k
     * @return матрица C = A x B размера n x k; элементы {@code long}, чтобы произведения int не переполнялись
     * @throws IllegalArgumentException если число столбцов A не равно числу строк B
     */
    public static long[][] multiply(int[][] left, int[][] right) {
        int inner = Matrices.requireRectangular(left);
        int cols = Matrices.requireRectangular(right);
        if (left.length > 0 && inner != right.length) {
            throw new IllegalArgumentException(
                    "Columns of A (" + inner + ") must equal rows of B (" + right.length + ")");
        }
        long[][] result = new long[left.length][cols];
        // Порядок циклов i-t-j: во внутреннем цикле строки right[t] и result[i] читаются
        // последовательно, что лучше для кэша, чем обход B по столбцам в порядке i-j-t.
        for (int i = 0; i < left.length; i++) {
            for (int t = 0; t < inner; t++) {
                long factor = left[i][t];
                for (int j = 0; j < cols; j++) {
                    result[i][j] += factor * right[t][j];
                }
            }
        }
        return result;
    }
}
