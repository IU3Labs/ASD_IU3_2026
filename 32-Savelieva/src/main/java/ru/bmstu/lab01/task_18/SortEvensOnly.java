/**
 * Задача 18. Сортировка только чётных.
 * Вход: массив из n целых чисел.
 * Выход: массив, в котором чётные элементы отсортированы по неубыванию,
 * а нечётные остались на своих позициях.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_18;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Сортировка чётных элементов с сохранением позиций нечётных.
 */
public final class SortEvensOnly {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private SortEvensOnly() {
    }

    /**
     * Сортирует чётные элементы, оставляя нечётные на их местах.
     *
     * @param data входной массив
     * @return новый массив с отсортированными чётными
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static int[] sortEvensOnly(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        int[] evens = new int[data.length];
        int evenCount = 0;
        for (int value : data) {
            if (value % 2 == 0) {
                evens[evenCount++] = value;
            }
        }
        // Сортируем только собранные чётные — подмассив [0, evenCount).
        Arrays.sort(evens, 0, evenCount);
        int[] result = data.clone();
        int cursor = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] % 2 == 0) {
                result[i] = evens[cursor++];
            }
        }
        return result;
    }

    /**
     * Точка входа. Формат ввода: n, затем n чисел.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: ожидалось целое число");
                return;
            }
            int n = scanner.nextInt();
            if (n < LOWER_BOUND || n > UPPER_BOUND) {
                System.out.println("Ошибка: n вне допустимого диапазона");
                return;
            }
            int[] data = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: ожидалось целое число");
                    return;
                }
                data[i] = scanner.nextInt();
            }
            int[] result = sortEvensOnly(data);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(result[i]);
            }
            System.out.println(sb);
        }
    }
}