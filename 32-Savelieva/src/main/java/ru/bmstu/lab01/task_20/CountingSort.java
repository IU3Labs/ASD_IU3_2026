/**
 * Задача 20. Сортировка подсчётом.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию.
 * Требование: реализовать сортировку подсчётом.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_20;

import java.util.Scanner;

/**
 * Сортировка подсчётом (counting sort).
 * Сложность O(n + k), где k — размах значений (max − min + 1).
 */
public final class CountingSort {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;
    private static final long MAX_RANGE = 1_000_000L;
    private CountingSort() {
    }

    /**
     * Сортирует массив подсчётом. Для отрицательных значений ключом служит
     * {@code value − min}, поэтому массив может содержать любые int.
     * Стабильность (раскладка справа налево) сохраняется, хотя для голых чисел она не видна.
     *
     * @param data входной массив
     * @return новый отсортированный массив
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static int[] sort(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        int min = data[0];
        int max = data[0];
        for (int value : data) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }
        long range = (long) max - min + 1;
        if (range > MAX_RANGE) {
            throw new IllegalArgumentException("слишком большой размах значений для сортировки подсчётом");
        }
        int intRange = (int) range;
        int[] count = new int[intRange];
        for (int value : data) {
            count[value - min]++;
        }
        // Префиксные суммы: count[k] = позиция, на которой заканчивается блок ключа k.
        for (int k = 1; k < range; k++) {
            count[k] += count[k - 1];
        }
        int[] result = new int[data.length];
        for (int i = data.length - 1; i >= 0; i--) {
            int key = data[i] - min;
            result[--count[key]] = data[i];
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
            int[] result = sort(data);
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