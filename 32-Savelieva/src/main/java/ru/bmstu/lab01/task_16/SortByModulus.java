/**
 * Задача 16. Сортировка по модулю.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию модуля.
 * При равных модулях первым идёт отрицательный элемент.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_16;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Сортировка массива по модулю с приоритетом отрицательного при равенстве.
 */
public final class SortByModulus {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    /**
     * Компаратор по |a|. Длину приводим к long, чтобы корректно обработать
     * Integer.MIN_VALUE: Math.abs от него остаётся отрицательным.
     */
    private static final Comparator<Integer> BY_ABSOLUTE_VALUE = (a, b) -> {
        int byMagnitude = Long.compare(Math.abs((long) a), Math.abs((long) b));
        if (byMagnitude != 0) {
            return byMagnitude;
        }
        return Boolean.compare(a < 0, b < 0) == 0 ? 0 : (a < 0 ? -1 : 1);
    };

    private SortByModulus() {
    }

    /**
     * Сортирует массив по неубыванию модуля; при равенстве модулей
     * отрицательный идёт раньше. Исходный массив не изменяется.
     *
     * @param data входной массив
     * @return новый отсортированный массив
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static int[] sortByModulus(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        Integer[] boxed = new Integer[data.length];
        for (int i = 0; i < data.length; i++) {
            boxed[i] = data[i];
        }
        Arrays.sort(boxed, BY_ABSOLUTE_VALUE);
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = boxed[i];
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
            int[] result = sortByModulus(data);
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