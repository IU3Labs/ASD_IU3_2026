/**
 * Задача 19. Объединение двух отсортированных массивов.
 * Вход: два массива, каждый отсортирован по неубыванию; размеры n и m.
 * Выход: один отсортированный массив — результат слияния.
 * Требование: O(n + m).
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_19;

import java.util.Scanner;

/**
 * Слияние двух отсортированных массивов за O(n + m).
 */
public final class MergeSorted {

    /** Минимально допустимая длина одного массива. */
    private static final int LOWER_BOUND = 0;

    /** Максимально допустимая длина одного массива. */
    private static final int UPPER_BOUND = 99_999;

    private MergeSorted() {
    }

    /**
     * Сливает два неубывающих массива в один неубывающий.
     * Два указателя, на каждом шаге берём меньший из текущих.
     *
     * @param left  первый отсортированный массив
     * @param right второй отсортированный массив
     * @return новый массив-слияние
     * @throws IllegalArgumentException если массив {@code null}, длины вне границ или массив не отсортирован
     */
    public static int[] merge(int[] left, int[] right) {
        check(left);
        check(right);
        requireSorted(left);
        requireSorted(right);

        int[] merged = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length || j < right.length) {
            if (j == right.length || (i < left.length && left[i] <= right[j])) {
                merged[k++] = left[i++];
            } else {
                merged[k++] = right[j++];
            }
        }
        return merged;
    }

    private static void check(int[] array) {
        if (array == null || array.length < LOWER_BOUND || array.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
    }

    private static void requireSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                throw new IllegalArgumentException("массив должен быть отсортирован");
            }
        }
    }

    /**
     * Точка входа. Формат ввода: n, n чисел, m, m чисел.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = readSize(scanner);
            if (n < 0) {
                return;
            }
            int[] left = readArray(scanner, n);
            if (left == null) {
                return;
            }
            int m = readSize(scanner);
            if (m < 0) {
                return;
            }
            int[] right = readArray(scanner, m);
            if (right == null) {
                return;
            }
            int[] merged = merge(left, right);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < merged.length; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(merged[i]);
            }
            System.out.println(sb);
        }
    }

    /** Читает размер массива; возвращает -1 при ошибке (сообщение уже напечатано). */
    private static int readSize(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: ожидалось целое число");
            return -1;
        }
        int size = scanner.nextInt();
        if (size < LOWER_BOUND || size > UPPER_BOUND) {
            System.out.println("Ошибка: размер вне допустимого диапазона");
            return -1;
        }
        return size;
    }

    /** Читает массив; возвращает null при ошибке (сообщение уже напечатано). */
    private static int[] readArray(Scanner scanner, int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: ожидалось целое число");
                return null;
            }
            result[i] = scanner.nextInt();
        }
        return result;
    }
}