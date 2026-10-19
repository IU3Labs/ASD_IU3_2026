package ru.bmstu.lab01.common;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Форматирует числа в одну строку через пробел.
 */
public final class OutputFormat {

    private static final String SEPARATOR = " ";

    private OutputFormat() {
    }

    /**
     * Объединяет числа через пробел; для пустого массива возвращает пустую строку.
     */
    public static String join(int[] values) {
        return joinStream(Arrays.stream(values).asLongStream());
    }

    /**
     * Объединяет числа через пробел; для пустого массива возвращает пустую строку.
     */
    public static String join(long[] values) {
        return joinStream(Arrays.stream(values));
    }

    private static String joinStream(LongStream values) {
        return values.mapToObj(String::valueOf).collect(Collectors.joining(SEPARATOR));
    }
}
