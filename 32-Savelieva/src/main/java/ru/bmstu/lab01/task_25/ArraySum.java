/**
 * Задача 25. Сумма элементов.
 * Вход: массив из n целых чисел.
 * Выход: сумма всех элементов.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_25;

import java.util.Scanner;

/**
 * Вычисление суммы элементов массива.
 */
public final class ArraySum {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private ArraySum() {
    }

    /**
     * Возвращает сумму элементов массива.
     *
     * <p>Сумма копится в {@code long}: при n = 10^5 и элементах около
     * {@link Integer#MAX_VALUE} результат не помещается в {@code int}
     * (n · 2^31 ≈ 2·10^14 &lt; 2^63, но &gt; 2^31).
     *
     * @param data входной массив
     * @return сумма элементов
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static long sum(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        long total = 0L;
        for (int value : data) {
            total += value;
        }
        return total;
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
            System.out.println(sum(data));
        }
    }
}