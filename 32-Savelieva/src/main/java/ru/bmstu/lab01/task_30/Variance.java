/**
 * Задача 30. Дисперсия массива.
 * Вход: массив из n целых чисел.
 * Выход: дисперсия — среднее квадратов отклонений от среднего.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_30;

import java.util.Scanner;

/**
 * Вычисление дисперсии массива.
 *
 * <p>Формула: D = (1/n) · Σ(a[i] − mean)^2, где mean = (1/n) · Σa[i].
 * Используется вычислительно устойчивый вариант «через отклонения»,
 * а не Σa[i]^2/n − mean^2: последний теряет точность при больших
 * близких значениях (разность двух больших чисел).
 */
public final class Variance {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private Variance() {
    }

    /**
     * Возвращает дисперсию массива (среднее квадратов отклонений от среднего).
     *
     * <p>Среднее вычисляется в {@code double}, отклонения — в {@code double},
     * квадраты суммируются в {@code double}. Сумма в {@code long} для среднего
     * не нужна: {@code double} даёт достаточную точность.
     *
     * @param data входной массив
     * @return дисперсия
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static double variance(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        double sum = 0.0;
        for (int value : data) {
            sum += value;
        }
        double mean = sum / data.length;
        double squaredDeviations = 0.0;
        for (int value : data) {
            double diff = value - mean;
            squaredDeviations += diff * diff;
        }
        return squaredDeviations / data.length;
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
            System.out.println(variance(data));
        }
    }
}