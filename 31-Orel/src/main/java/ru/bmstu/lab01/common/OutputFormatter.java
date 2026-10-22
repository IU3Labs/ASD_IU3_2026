package ru.bmstu.lab01.common;

import java.util.StringJoiner;

/**
 * Преобразование массивов и матриц в текст для вывода: элементы строки разделяются пробелом,
 * строки матрицы — переводом строки.
 */
public final class OutputFormatter {

    private static final String SEPARATOR = " ";

    private OutputFormatter() {
    }

    /**
     * Форматирует массив {@code int} в одну строку.
     *
     * @param array массив
     * @return элементы через пробел (пустая строка для пустого массива)
     */
    public static String join(int[] array) {
        StringJoiner joiner = new StringJoiner(SEPARATOR);
        for (int value : array) {
            joiner.add(Integer.toString(value));
        }
        return joiner.toString();
    }

    /**
     * Форматирует массив {@code long} в одну строку.
     *
     * @param array массив
     * @return элементы через пробел (пустая строка для пустого массива)
     */
    public static String join(long[] array) {
        StringJoiner joiner = new StringJoiner(SEPARATOR);
        for (long value : array) {
            joiner.add(Long.toString(value));
        }
        return joiner.toString();
    }

    /**
     * Форматирует матрицу {@code long}: каждая строка матрицы — отдельная строка текста.
     *
     * @param matrix матрица
     * @return текстовое представление матрицы
     */
    public static String join(long[][] matrix) {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (long[] row : matrix) {
            joiner.add(join(row));
        }
        return joiner.toString();
    }
}
