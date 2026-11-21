/*Задача 28. Медиана массива
Вход: массив из (n) целых чисел.
Выход: медиана массива. При нечётном (n) — элемент посередине после сортировки. При чётном (n) —
среднее арифметическое двух центральных элементов (как число с плавающей точкой).
Сложность: средняя */

package ru.bmstu.lab1.task_28;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Решение задачи 28: медиана массива после сортировки.
 */
public class Median {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает медиану. При нечётном n печатается целое число,
     * при чётном — среднее двух центральных элементов с одним знаком после точки (например, 2.5).
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

            // Читаем сам массив
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i] = scanner.nextInt();
            }

            Arrays.sort(a);

            if (n % 2 == 1) {
                System.out.println(a[n / 2]);
            } else {
                // Сумма двух int может не поместиться в int — складываем в long.
                // s / 2.0 точно представимо в double (|s| <= 2^32), а Locale.ROOT
                // гарантирует точку, а не запятую, как разделитель
                long s = (long) a[n / 2 - 1] + a[n / 2];
                System.out.println(String.format(Locale.ROOT, "%.1f", s / 2.0));
            }
        }
    }
}
