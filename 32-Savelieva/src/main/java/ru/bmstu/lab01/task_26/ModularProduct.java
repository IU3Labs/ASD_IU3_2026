/**
 * Задача 26. Произведение элементов по модулю.
 * Вход: массив из n целых чисел.
 * Выход: произведение всех элементов по модулю 10^9 + 7.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_26;

import java.util.Scanner;

/**
 * Произведение элементов массива по модулю 10^9 + 7.
 */
public final class ModularProduct {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    /** Модуль, по которому берётся произведение. */
    private static final long MOD = 1_000_000_007L;

    private ModularProduct() {
    }

    /**
     * Возвращает произведение элементов массива по модулю {@link #MOD}.
     *
     * <p>После каждого умножения берётся остаток по модулю — иначе произведение
     * вышло бы за пределы {@code long} уже при десятке элементов.
     * Отрицательные произведения приводятся к диапазону [0, MOD).
     *
     * @param data входной массив
     * @return произведение по модулю
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static long product(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        long result = 1L;
        for (int value : data) {
            // long-умножение безопасно: (MOD-1)^2 ≈ 10^18 < Long.MAX_VALUE ≈ 9.2·10^18
            result = (result * (value % MOD + MOD)) % MOD;
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
            System.out.println(product(data));
        }
    }
}
