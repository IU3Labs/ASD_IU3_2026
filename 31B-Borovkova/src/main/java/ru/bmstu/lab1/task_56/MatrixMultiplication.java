package ru.bmstu.lab1.task_56;

import java.util.Scanner;

/**
 * Решение задачи 56: умножение матриц по определению (строка на столбец).
 */
public class MatrixMultiplication {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы (чтобы n * m * k операций выполнялись быстро). */
    private static final int MAX_SIZE = 500;
    /** Наибольшее допустимое по модулю значение элемента, чтобы сумма произведений поместилась в long. */
    private static final int MAX_ABS_VALUE = 1_000_000;
    /** Начальное значение суммы: пока не сложено ни одного произведения. */
    private static final int SUM_COUNT = 0;
    /** Признак ошибки при чтении размера. */
    private static final int INVALID = -1;

    /**
     * Читает размеры и матрицу A, затем размеры и матрицу B, печатает произведение C = A x B.
     * Число столбцов A должно совпадать с числом строк B.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Матрица A размера n x m
        int n = readSize(scanner);
        if (n == INVALID) {
            return;
        }
        int m = readSize(scanner);
        if (m == INVALID) {
            return;
        }
        int[][] a = readMatrix(scanner, n, m);
        if (a == null) {
            return;
        }

        // Матрица B размера p x k, умножение возможно только при p == m
        int p = readSize(scanner);
        if (p == INVALID) {
            return;
        }
        int k = readSize(scanner);
        if (k == INVALID) {
            return;
        }
        if (p != m) {
            System.out.println("Ошибка: число столбцов A должно равняться числу строк B");
            return;
        }
        int[][] b = readMatrix(scanner, p, k);
        if (b == null) {
            return;
        }

        // C[i][j] — сумма произведений i-й строки A на j-й столбец B
        long[][] c = new long[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                long s = SUM_COUNT;
                for (int t = 0; t < m; t++) {
                    s = s + (long) a[i][t] * b[t][j];
                }
                c[i][j] = s;
            }
        }

        // Печатаем матрицу C: числа в строке через пробел, каждая строка с новой строки
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                System.out.print(c[i][j]);
                if (j < k - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }

    /**
     * Читает один размер матрицы и проверяет его.
     *
     * @param scanner источник ввода
     * @return размер или INVALID, если ввод некорректен (сообщение об ошибке уже напечатано)
     */
    private static int readSize(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return INVALID;
        }
        int size = scanner.nextInt();
        if (size < MIN_SIZE || size > MAX_SIZE) {
            System.out.println("Ошибка: некорректный размер матрицы");
            return INVALID;
        }
        return size;
    }

    /**
     * Читает матрицу rows x cols построчно и проверяет каждый элемент.
     *
     * @param scanner источник ввода
     * @param rows    число строк
     * @param cols    число столбцов
     * @return матрица или null, если ввод некорректен (сообщение об ошибке уже напечатано)
     */
    private static int[][] readMatrix(Scanner scanner, int rows, int cols) {
        int[][] a = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return null;
                }
                a[i][j] = scanner.nextInt();
                if (a[i][j] < -MAX_ABS_VALUE || a[i][j] > MAX_ABS_VALUE) {
                    System.out.println("Ошибка: элемент слишком большой по модулю");
                    return null;
                }
            }
        }
        return a;
    }
}