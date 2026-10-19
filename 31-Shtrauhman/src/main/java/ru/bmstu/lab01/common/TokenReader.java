package ru.bmstu.lab01.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public final class TokenReader {

    private final BufferedReader reader;
    private StringTokenizer tokenizer = new StringTokenizer("");

    public TokenReader(InputStream in) {
        this.reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    /**
     * Возвращает следующее слово. Бросает NoSuchElementException, если ввод закончился.
     */
    public String next() {
        try {
            while (!tokenizer.hasMoreTokens()) {
                String line = reader.readLine();
                if (line == null) {
                    throw new NoSuchElementException("Unexpected end of input");
                }
                tokenizer = new StringTokenizer(line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return tokenizer.nextToken();
    }

    /**
     * Читает следующее слово как int. Бросает NumberFormatException, если это не число.
     */
    public int nextInt() {
        return Integer.parseInt(next());
    }

    /**
     * Читает размер массива или матрицы. Бросает IllegalArgumentException, если он меньше 1.
     */
    public int nextSize() {
        int size = nextInt();
        if (size < 1) {
            throw new IllegalArgumentException("Size must be positive: " + size);
        }
        return size;
    }

    public int[] nextIntArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = nextInt();
        }
        return array;
    }

    /**
     * Читает длину n, затем n целых чисел (формат ввода задач 25, 30 и 32).
     */
    public int[] nextSizedIntArray() {
        return nextIntArray(nextSize());
    }

    /**
     * Читает матрицу построчно: rows строк по cols чисел.
     */
    public int[][] nextIntMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][];
        for (int i = 0; i < rows; i++) {
            matrix[i] = nextIntArray(cols);
        }
        return matrix;
    }
}
