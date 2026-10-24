/**
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 * Ограничения: 1 < n < 10^5.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_1;

import java.util.Scanner;

/**
 * Поиск минимального и максимального элементов массива за один проход.
 */
public final class MinMaxSearch {

    /** Минимально допустимое n: по условию строго больше 1. */
    private static final int LOWER_BOUND = 2;

    /** Максимально допустимое n: по условию строго меньше 10^5. */
    private static final int UPPER_BOUND = 99_999;

    private MinMaxSearch() {
    }

    /**
     * Ищет минимум и максимум в массиве.
     *
     * @param data массив, длина должна быть в допустимых границах
     * @return пара {минимум, максимум}
     * @throws IllegalArgumentException если массив {@code null} или его длина вне [2; 99 999]
     */
    public static int[] search(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        int min = data[0];
        int max = data[0];
        for (int i = 1; i < data.length; i++) {
            int value = data[i];
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }
        return new int[] {min, max};
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
            int[] result = search(data);
            System.out.println(result[0] + " " + result[1]);
        }
    }
}
