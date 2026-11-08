/**
 * Задача 32. Сумма произведений пар.
 * Вход: массив из n целых чисел.
 * Выход: сумма произведений всех пар a[i]·a[j] для i < j.
 * Требование: O(n). Сложность: высокая.
 */
package ru.bmstu.lab01.task_32;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Сумма произведений всех пар элементов массива за O(n).
 *
 * <p>Используется тождество:
 * Σ<sub>i&lt;j</sub> a[i]·a[j] = ((Σa[i])² − Σa[i]²) / 2.
 * Оно позволяет обойтись одним проходом вместо перебора пар O(n²).
 */
public final class SumOfPairProducts {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private SumOfPairProducts() {
    }

    /**
     * Вычисляет сумму произведений всех пар {@code i < j}.
     *
     * <p>Формула: {@code (S² − Q) / 2}, где S = сумма элементов, Q = сумма квадратов.
     * Используется {@link BigInteger}, потому что S² при n = 10^5 и элементах
     * около {@code Integer.MAX_VALUE} достигает 4.6·10^23 — за пределами {@code long}.
     *
     * @param data входной массив
     * @return сумма произведений всех пар
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static BigInteger sumOfPairProducts(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        BigInteger sum = BigInteger.ZERO;
        BigInteger sumOfSquares = BigInteger.ZERO;
        for (int value : data) {
            BigInteger bigValue = BigInteger.valueOf(value);
            sum = sum.add(bigValue);
            sumOfSquares = sumOfSquares.add(bigValue.multiply(bigValue));
        }
        // (S² - Q) / 2 — числитель всегда чётный, деление точное
        return sum.multiply(sum).subtract(sumOfSquares).divide(BigInteger.TWO);
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
            System.out.println(sumOfPairProducts(data));
        }
    }
}