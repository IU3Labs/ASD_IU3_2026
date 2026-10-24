/**
 * Задача 21. K-й наименьший элемент.
 * Вход: массив из n целых чисел и k (1 ≤ k ≤ n).
 * Выход: k-й наименьший элемент массива (по неубыванию, с учётом повторов).
 * Требование: отсортировать и взять элемент с индексом k − 1.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_21;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Поиск k-го наименьшего элемента через сортировку копии массива.
 */
public final class KthSmallest {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private KthSmallest() {
    }

    /**
     * Возвращает k-й наименьший элемент (1-based).
     * Сортируем копию, чтобы не менять входной массив.
     *
     * @param data входной массив
     * @param k    порядковый номер, {@code 1 ≤ k ≤ data.length}
     * @return k-й наименьший элемент
     * @throws IllegalArgumentException если массив {@code null}, длина вне границ или k вне [1; n]
     */
    public static int findKthSmallest(int[] data, int k) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        if (k < 1 || k > data.length) {
            throw new IllegalArgumentException("k вне диапазона [1; n]");
        }
        int[] sorted = data.clone();
        Arrays.sort(sorted);
        return sorted[k - 1];
    }

    /**
     * Точка входа. Формат ввода: n, n чисел, k.
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
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: ожидалось целое число");
                return;
            }
            int k = scanner.nextInt();
            if (k < 1 || k > n) {
                System.out.println("Ошибка: k вне диапазона [1; n]");
                return;
            }
            System.out.println(findKthSmallest(data, k));
        }
    }
}