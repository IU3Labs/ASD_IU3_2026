package ru.bmstu.lab01.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Чтение целых чисел, массивов и матриц из входного потока.
 *
 * <p>Используется {@link BufferedReader} + {@link StringTokenizer}: в отличие от {@code Scanner}
 * такой ввод быстро читает до 10^5 чисел. Числа могут быть разделены любыми пробельными символами
 * и переводами строк.
 */
public final class InputReader {

    private final BufferedReader reader;
    private StringTokenizer tokenizer;

    /**
     * Создаёт читатель поверх потока.
     *
     * @param input источник данных (обычно {@code System.in})
     */
    public InputReader(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    /**
     * Читает следующее целое число.
     *
     * @return прочитанное число
     * @throws NoSuchElementException если данные закончились
     * @throws NumberFormatException  если очередной токен не является целым числом
     */
    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    /**
     * Читает неотрицательный размер (длину массива, число строк или столбцов).
     *
     * @return прочитанный размер
     * @throws IllegalArgumentException если размер отрицательный
     */
    public int nextSize() {
        int size = nextInt();
        if (size < 0) {
            throw new IllegalArgumentException("Size must be non-negative: " + size);
        }
        return size;
    }

    /**
     * Читает массив: сначала длину {@code n}, затем {@code n} чисел.
     *
     * @return прочитанный массив
     */
    public int[] nextArray() {
        return nextArray(nextSize());
    }

    /**
     * Читает {@code length} чисел подряд.
     *
     * @param length количество чисел
     * @return прочитанный массив
     */
    public int[] nextArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = nextInt();
        }
        return array;
    }

    /**
     * Читает матрицу: сначала размеры {@code n m}, затем {@code n * m} чисел построчно.
     *
     * @return прочитанная матрица
     */
    public int[][] nextMatrix() {
        int rows = nextSize();
        int cols = nextSize();
        return nextMatrix(rows, cols);
    }

    /**
     * Читает матрицу заданного размера построчно.
     *
     * @param rows число строк
     * @param cols число столбцов
     * @return прочитанная матрица
     */
    public int[][] nextMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][];
        for (int i = 0; i < rows; i++) {
            matrix[i] = nextArray(cols);
        }
        return matrix;
    }

    private String nextToken() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            String line = readLine();
            if (line == null) {
                throw new NoSuchElementException("Unexpected end of input");
            }
            tokenizer = new StringTokenizer(line);
        }
        return tokenizer.nextToken();
    }

    private String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
