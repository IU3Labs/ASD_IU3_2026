/**
 * Задача 17. Сортировка по сумме цифр.
 * Вход: массив из n неотрицательных целых чисел.
 * Выход: массив, отсортированный по возрастанию суммы цифр.
 * При равных суммах исходный порядок сохраняется (стабильная сортировка).
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_17;

import java.util.Scanner;

/**
 * Стабильная сортировка по сумме цифр через подсчёт.
 */
public final class SortByDigitSum {

    /** Максимальная сумма цифр для int: 10 цифр по 9 = 90. */
    private static final int MAX_DIGIT_SUM = 90;

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private SortByDigitSum() {
    }

    /**
     * Стабильная сортировка по возрастанию суммы цифр.
     * Ключ лежит в 0..90, поэтому сортировка подсчётом даёт O(n) вместо O(n log n).
     *
     * @param data входной массив неотрицательных чисел
     * @return новый отсортированный массив
     * @throws IllegalArgumentException если массив {@code null}, длина вне границ или есть отрицательные
     */
    public static int[] sortByDigitSum(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        int[] digitSums = new int[data.length];
        int[] count = new int[MAX_DIGIT_SUM + 1];
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                throw new IllegalArgumentException("числа должны быть неотрицательными");
            }
            int sum = sumOfDigits(data[i]);
            digitSums[i] = sum;
            count[sum]++;
        }
        // Префиксные суммы: count[k] станет позицией, с которой начинается блок суммы k.
        for (int k = 1; k <= MAX_DIGIT_SUM; k++) {
            count[k] += count[k - 1];
        }
        int[] result = new int[data.length];
        // Идём справа налево — это сохраняет исходный порядок для равных сумм (стабильность).
        for (int i = data.length - 1; i >= 0; i--) {
            int key = digitSums[i];
            result[--count[key]] = data[i];
        }
        return result;
    }

    /** Сумма цифр неотрицательного числа. */
    private static int sumOfDigits(int value) {
        int sum = 0;
        while (value > 0) {
            sum += value % 10;
            value /= 10;
        }
        return sum;
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
                if (data[i] < 0) {
                    System.out.println("Ошибка: числа должны быть неотрицательными");
                    return;
                }
            }
            int[] result = sortByDigitSum(data);
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