package ru.bmstu.lab1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.NoSuchElementException;

/** Читает целые числа из стандартного ввода без накладных расходов Scanner. */
public final class InputReader {
    private final BufferedInputStream input = new BufferedInputStream(System.in);

    /** Считывает следующее целое число со знаком. */
    public int nextInt() {
        int current = readByte();
        while (current != -1 && current <= ' ') {
            current = readByte();
        }
        if (current == -1) {
            throw new NoSuchElementException("Expected an integer");
        }
        int sign = 1;
        if (current == '-') {
            sign = -1;
            current = readByte();
        }
        int result = 0;
        while (current >= '0' && current <= '9') {
            result = result * 10 + (current - '0');
            current = readByte();
        }
        return result * sign;
    }

    /** Считывает массив целых чисел заданной длины. */
    public int[] nextArray(int length) {
        int[] values = new int[length];
        for (int i = 0; i < length; i++) {
            values[i] = nextInt();
        }
        return values;
    }

    /** Считывает целочисленную матрицу построчно. */
    public int[][] nextMatrix(int rows, int columns) {
        int[][] matrix = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            matrix[row] = nextArray(columns);
        }
        return matrix;
    }

    private int readByte() {
        try {
            return input.read();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
