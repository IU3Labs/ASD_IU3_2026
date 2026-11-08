/**
 * Задача 56. Умножение матриц.
 * Вход: матрица A размера n x m и матрица B размера m x k.
 * Выход: матрица C = A x B размера n x k.
 * Требование: O(n·m·k) по времени, O(n·k) по памяти.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_56;

import java.util.Scanner;

/**
 * Умножение матриц по определению.
 */
public final class MatrixMultiplication {

    /** Минимально допустимый размер матрицы по любому измерению. */
    private static final int MIN_DIM = 1;

    /** Максимально допустимый размер матрицы по любому измерению. */
    private static final int MAX_DIM = 200;

    private MatrixMultiplication() {
    }

    /**
     * Умножает матрицу A (n × m) на матрицу B (m × k).
     *
     * <p>Реализовано «по определению»: {@code c[i][j] = Σ A[i][t] · B[t][j]}.
     * Внутренние суммы копятся в {@code long}: при m до 200 и элементах около
     * {@code Integer.MAX_VALUE} сумма может достигать 4·10^11 — выходит за {@code int}.
     * Возвращаемый массив — {@code int[][]}, поэтому результат приводится обратно;
     * если итог не помещается в {@code int}, бросается {@link ArithmeticException}.
     *
     * @param left  матрица A (n × m)
     * @param right матрица B (m × k)
     * @return матрица C (n × k)
     * @throws IllegalArgumentException если матрицы {@code null}, несовместимых размеров
     *                                  или размеры вне допустимых границ
     * @throws ArithmeticException      если элемент результата не помещается в {@code int}
     */
    public static int[][] multiply(int[][] left, int[][] right) {
        requireValid(left);
        requireValid(right);
        int n = left.length;
        int m = left[0].length;
        int k = right[0].length;
        if (right.length != m) {
            throw new IllegalArgumentException(
                    "число строк B должно совпадать с числом столбцов A");
        }

        int[][] result = new int[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                long sum = 0L;
                for (int t = 0; t < m; t++) {
                    sum += (long) left[i][t] * right[t][j];
                }
                if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
                    throw new ArithmeticException("элемент результата выходит за пределы int");
                }
                result[i][j] = (int) sum;
            }
        }
        return result;
    }

    private static void requireValid(int[][] matrix) {
        if (matrix == null || matrix.length < MIN_DIM || matrix.length > MAX_DIM) {
            throw new IllegalArgumentException("некорректное число строк");
        }
        int cols = -1;
        for (int[] row : matrix) {
            if (row == null || row.length < MIN_DIM || row.length > MAX_DIM) {
                throw new IllegalArgumentException("некорректная длина строки");
            }
            if (cols == -1) {
                cols = row.length;
            } else if (row.length != cols) {
                throw new IllegalArgumentException("строки разной длины");
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = readDim(scanner, "n");
            if (n < 0) {
                return;
            }
            int m = readDim(scanner, "m");
            if (m < 0) {
                return;
            }
            int k = readDim(scanner, "k");
            if (k < 0) {
                return;
            }
            int[][] left = readMatrix(scanner, n, m);
            if (left == null) {
                return;
            }
            int[][] right = readMatrix(scanner, m, k);
            if (right == null) {
                return;
            }
            int[][] product;
            try {
                product = multiply(left, right);
            } catch (ArithmeticException e) {
                System.out.println("Ошибка: " + e.getMessage());
                return;
            }
            printMatrix(product);
        }
    }

    private static int readDim(Scanner scanner, String name) {
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: ожидалось целое число");
            return -1;
        }
        int value = scanner.nextInt();
        if (value < MIN_DIM || value > MAX_DIM) {
            System.out.println("Ошибка: " + name + " вне допустимого диапазона");
            return -1;
        }
        return value;
    }

    private static int[][] readMatrix(Scanner scanner, int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: ожидалось целое число");
                    return null;
                }
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            if (i > 0) {
                sb.append('\n');
            }
            for (int j = 0; j < matrix[i].length; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(matrix[i][j]);
            }
        }
        System.out.println(sb);
    }
}