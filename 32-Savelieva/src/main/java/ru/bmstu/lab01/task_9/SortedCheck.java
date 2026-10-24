/**
 * Задача 9. Проверка отсортированности.
 * Вход: массив из n целых чисел.
 * Выход: YES, если массив неубывающий; NO иначе.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_9;

import java.util.Scanner;

/**
 * Проверка массива на неубывающий порядок.
 */
public final class SortedCheck {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private SortedCheck() {
    }

    /**
     * Проверяет, что каждый следующий элемент не меньше предыдущего.
     * Ранний выход при первом нарушении — дальше проверять бессмысленно.
     *
     * @param data входной массив
     * @return {@code true}, если массив неубывающий
     * @throws IllegalArgumentException если массив {@code null} или его длина вне допустимых границ
     */
    public static boolean isNonDecreasing(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        for (int i = 1; i < data.length; i++) {
            if (data[i] < data[i - 1]) {
                return false;
            }
        }
        return true;
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
            System.out.println(isNonDecreasing(data) ? "YES" : "NO");
        }
    }
}
