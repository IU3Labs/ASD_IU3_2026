package ru.bmstu.lab1.common;

import java.util.Scanner;
import java.util.StringJoiner;

/** Общие операции чтения массива и форматирования ответа для задач лабораторной. */
public final class ConsoleIo {
    private static final int MIN_SIZE = 2;
    private static final int MAX_SIZE_EXCLUSIVE = 100_000;

    private ConsoleIo() {}

    /** Читает размер и элементы массива, проверяя ограничение 1 < n < 100000. */
    public static int[] readArray(Scanner scanner) {
        int size = readInt(scanner, "Введите длину массива");
        if (size < MIN_SIZE || size >= MAX_SIZE_EXCLUSIVE) {
            throw new IllegalArgumentException(
                    "некорректная длина массива: допустимо от 2 до 99999");
        }
        System.out.println("Введите массив");
        int[] values = new int[size];
        for (int index = 0; index < size; index++) {
            values[index] = readInt(scanner);
        }
        return values;
    }

    /** Запрашивает целое число типа int, проверяя его наличие и диапазон. */
    public static int readInt(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return readInt(scanner);
    }

    private static int readInt(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            throw new IllegalArgumentException("должно быть целое число!");
        }
        return scanner.nextInt();
    }

    /** Запрашивает целое число типа long для суммы двух значений int. */
    public static long readLong(Scanner scanner, String prompt) {
        System.out.println(prompt);
        if (!scanner.hasNextLong()) {
            throw new IllegalArgumentException("должно быть целое число!");
        }
        return scanner.nextLong();
    }

    /** Форматирует массив через пробел; для пустого массива возвращает пустую строку. */
    public static String format(int[] values) {
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                result.append(' ');
            }
            result.append(values[index]);
        }
        return result.toString();
    }

    /** Форматирует суммы типа long через пробел, включая значения вне диапазона int. */
    public static String format(long[] values) {
        StringJoiner result = new StringJoiner(" ");
        for (long value : values) {
            result.add(Long.toString(value));
        }
        return result.toString();
    }
}
