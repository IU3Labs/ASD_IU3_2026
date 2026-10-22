package ru.bmstu.lab1.common;

import java.io.PrintStream;

/**
 * Вывод массивов и матриц: элементы разделяются пробелом, каждая строка матрицы — с новой строки.
 */
public final class OutputWriter {

    private OutputWriter() {
    }

    /**
     * Печатает массив в одну строку через пробел.
     *
     * @param out   поток вывода
     * @param array массив для печати
     */
    public static void printArray(PrintStream out, int[] array) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                line.append(' ');
            }
            line.append(array[i]);
        }
        out.println(line);
    }

    /**
     * Печатает матрицу: каждая строка матрицы — отдельная строка вывода.
     *
     * @param out    поток вывода
     * @param matrix матрица для печати
     */
    public static void printMatrix(PrintStream out, int[][] matrix) {
        for (int[] row : matrix) {
            printArray(out, row);
        }
    }
}
