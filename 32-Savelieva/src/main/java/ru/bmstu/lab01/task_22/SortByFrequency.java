/**
 * Задача 22. Сортировка по частоте.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по убыванию частоты.
 * При равных частотах — по возрастанию значения.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_22;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Сортировка массива по частоте встречаемости.
 */
public final class SortByFrequency {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private SortByFrequency() {
    }

    /**
     * Сортирует элементы по убыванию частоты, при равенстве — по возрастанию значения.
     *
     * @param data входной массив
     * @return новый массив, сгруппированный по частоте
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static int[] sortByFrequency(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int value : data) {
            frequency.merge(value, 1, Integer::sum);
        }
        List<Integer> distinct = new ArrayList<>(frequency.keySet());
        distinct.sort(Comparator
                .comparingInt((Integer value) -> frequency.get(value)).reversed()
                .thenComparingInt(value -> value));
        int[] result = new int[data.length];
        int position = 0;
        for (int value : distinct) {
            int repeats = frequency.get(value);
            for (int r = 0; r < repeats; r++) {
                result[position++] = value;
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
            int[] result = sortByFrequency(data);
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