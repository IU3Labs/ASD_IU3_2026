/**
 * Задача 31. Скалярное произведение.
 * Вход: два массива одинаковой длины n.
 * Выход: скалярное произведение — сумма a[i]·b[i].
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_31;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Скалярное произведение двух массивов.
 */
public final class DotProduct {

    /** Минимально допустимая длина массивов. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимая длина массивов. */
    private static final int UPPER_BOUND = 99_999;

    private DotProduct() {
    }

    /**
     * Вычисляет скалярное произведение двух массивов одинаковой длины.
     *
     * <p>Результат накапливается в {@link BigInteger}: при n = 10^5 и элементах
     * около {@code Integer.MAX_VALUE} каждое произведение ≈ 4.6·10^18 (близко
     * к {@code Long.MAX_VALUE}), а их сумма достигает 4.6·10^23 — это выходит
     * за пределы {@code long}.
     *
     * @param left  первый массив
     * @param right второй массив той же длины
     * @return скалярное произведение
     * @throws IllegalArgumentException если массив {@code null}, длины не совпадают
     *                                  или выходят за допустимые границы
     */
    public static BigInteger dotProduct(int[] left, int[] right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("массивы не могут быть null");
        }
        if (left.length != right.length) {
            throw new IllegalArgumentException("длины массивов должны совпадать");
        }
        if (left.length < LOWER_BOUND || left.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массивов");
        }
        BigInteger total = BigInteger.ZERO;
        for (int i = 0; i < left.length; i++) {
            BigInteger product = BigInteger.valueOf(left[i])
                    .multiply(BigInteger.valueOf(right[i]));
            total = total.add(product);
        }
        return total;
    }

    /**
     * Точка входа. Формат ввода: n, n чисел первого массива, n чисел второго.
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
            int[] left = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: ожидалось целое число");
                    return;
                }
                left[i] = scanner.nextInt();
            }
            int[] right = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: ожидалось целое число");
                    return;
                }
                right[i] = scanner.nextInt();
            }
            System.out.println(dotProduct(left, right));
        }
    }
}