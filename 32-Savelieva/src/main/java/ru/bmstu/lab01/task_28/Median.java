/**
 * Задача 28. Медиана массива.
 * Вход: массив из n целых чисел.
 * Выход: медиана. При нечётном n — центральный элемент после сортировки.
 * При чётном n — среднее арифметическое двух центральных как double.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_28;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Вычисление медианы массива.
 */
public final class Median {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private Median() {
    }

    /**
     * Возвращает медиану массива.
     *
     * <p>Сортируем копию (входной массив не меняем). При нечётном n берём
     * центральный элемент; при чётном — среднее двух центральных как {@code double}.
     * Копия нужна, потому что {@link Arrays#sort(int[])} работает in-place.
     *
     * @param data входной массив
     * @return медиана
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static double median(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        int[] sorted = data.clone();
        Arrays.sort(sorted);
        int mid = sorted.length / 2;
        if (sorted.length % 2 == 1) {
            return sorted[mid];
        }
        // Приводим к long, чтобы сумма двух крайних int не переполнилась
        return ((long) sorted[mid - 1] + sorted[mid]) / 2.0;
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
            System.out.println(median(data));
        }
    }
}
