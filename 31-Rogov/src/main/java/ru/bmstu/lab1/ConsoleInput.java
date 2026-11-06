package ru.bmstu.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/** Общий ввод и форматирование для задач лабораторной №1. */
public final class ConsoleInput {
    private final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    private StringTokenizer tokens = new StringTokenizer("");

    /** Читает очередное целое число; пробелы и переводы строк равнозначны. */
    public long readLong() throws IOException {
        while (!tokens.hasMoreTokens()) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Недостаточно входных данных");
            }
            tokens = new StringTokenizer(line);
        }
        return Long.parseLong(tokens.nextToken());
    }

    /** Читает число в диапазоне int. */
    public int readInt() throws IOException {
        return Math.toIntExact(readLong());
    }

    /** Читает n, затем n элементов массива. */
    public int[] readArray() throws IOException {
        int size = readInt();
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = readInt();
        }
        return values;
    }

    /** Форматирует массив через пробел; пустой массив даёт пустую строку. */
    public static String format(int[] values) {
        StringJoiner result = new StringJoiner(" ");
        for (int value : values) {
            result.add(Integer.toString(value));
        }
        return result.toString();
    }
}
