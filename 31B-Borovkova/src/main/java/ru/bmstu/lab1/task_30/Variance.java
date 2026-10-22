/*Задача 30. Дисперсия
Вход: массив из (n) целых чисел.
Выход: выборочную дисперсию по формуле.
Сложность: средняя */

package ru.bmstu.lab1.task_30;

import java.util.Locale;
import java.util.Scanner;

/**
 * Решение задачи 30: выборочная дисперсия D = (1/n) · Σ(a_i − x̄)^2 в два прохода.
 */
public class Variance {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает выборочную дисперсию
     * D = (1/n) · Σ(a_i − x̄)^2, где x̄ — среднее арифметическое, с 6 знаками после точки.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int n = scanner.nextInt();
            if (n < MIN_LENGTH || n > MAX_LENGTH) {
                System.out.println("Ошибка: некорректная длина массива");
                return;
            }

            // Читаем массив и сразу считаем сумму (в long, чтобы не переполнить int)
            int[] a = new int[n];
            long s = 0;
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i] = scanner.nextInt();
                s = s + a[i];
            }

            // Второй проход: сумма квадратов отклонений от среднего
            double mean = (double) s / n;
            double d = 0;
            for (int i = 0; i < n; i++) {
                d = d + (a[i] - mean) * (a[i] - mean);
            }
            d = d / n;

            // Locale.ROOT — чтобы разделителем была точка, а не запятая
            System.out.println(String.format(Locale.ROOT, "%.6f", d));
        }
    }
}
