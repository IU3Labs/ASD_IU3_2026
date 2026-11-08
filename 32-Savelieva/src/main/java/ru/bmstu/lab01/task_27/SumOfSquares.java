/**
 * Задача 27. Сумма квадратов.
 * Вход: массив из n целых чисел.
 * Выход: сумма квадратов всех элементов.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_27;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Вычисление суммы квадратов элементов массива.
 */
public final class SumOfSquares {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private SumOfSquares() {
    }

    /**
     * Возвращает сумму квадратов элементов.
     *
     * <p>Возвращается {@link BigInteger}: при n = 10^5 и элементах около
     * {@code Integer.MAX_VALUE} сумма квадратов достигает ~10^5 · (2^31)^2 ≈ 4.6·10^23,
     * что не помещается ни в {@code int}, ни в {@code long} (максимум ~9.2·10^18).
     *
     * @param data входной массив
     * @return сумма квадратов
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static BigInteger sumOfSquares(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        BigInteger total = BigInteger.ZERO;
        for (int value : data) {
            BigInteger square = BigInteger.valueOf(value).pow(2);
            total = total.add(square);
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
            System.out.println(sumOfSquares(data));
        }
    }
}