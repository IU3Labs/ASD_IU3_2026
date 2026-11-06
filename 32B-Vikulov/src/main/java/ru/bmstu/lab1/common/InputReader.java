package ru.bmstu.lab1.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

/**
 * Чтение целых чисел, массивов и матриц из потока ввода.
 *
 * <p>Числа могут быть разделены любыми пробельными символами и переводами строк.
 */
public final class InputReader {

    private final BufferedReader reader;
    private StringTokenizer tokenizer;

    /**
     * Создаёт читателя поверх потока ввода.
     *
     * @param stream поток ввода (обычно {@code System.in})
     */
    public InputReader(InputStream stream) {
        this.reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    /**
     * Читает следующее целое число.
     *
     * @return прочитанное число
     * @throws IllegalStateException если ввод закончился
     */
    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    /**
     * Читает {@code size} целых чисел подряд.
     *
     * @param size количество чисел
     * @return массив прочитанных чисел
     */
    public int[] nextIntArray(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = nextInt();
        }
        return result;
    }

    /**
     * Читает матрицу построчно.
     *
     * @param rows    число строк
     * @param columns число столбцов
     * @return матрица {@code rows x columns}
     */
    public int[][] nextMatrix(int rows, int columns) {
        int[][] result = new int[rows][];
        for (int i = 0; i < rows; i++) {
            result[i] = nextIntArray(columns);
        }
        return result;
    }

    private String nextToken() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(readLine());
        }
        return tokenizer.nextToken();
    }

    private String readLine() {
        try {
            String line = reader.readLine();
            if (line == null) {
                throw new IllegalStateException("Unexpected end of input");
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
