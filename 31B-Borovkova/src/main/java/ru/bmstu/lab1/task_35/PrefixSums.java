/*Задача 35. Префиксные суммы
Вход: массив из (n) целых чисел и (q) запросов вида ([l, r]) — диапазон индексов.
Выход: для каждого запроса — сумму элементов с индексами от (l) до (r) включительно.
Требование: предпосчёт префиксных сумм, затем (O(1)) на запрос.
Сложность: средняя */

package ru.bmstu.lab1.task_35;

import java.util.Scanner;

/**
 * Решение задачи 35: суммы на отрезках через префиксные суммы, O(1) на запрос.
 */
public class PrefixSums {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Наименьшее допустимое количество запросов. */
    private static final int MIN_QUERIES = 1;
    /** Наибольшее допустимое количество запросов. */
    private static final int MAX_QUERIES = 99_999;

    /**
     * Читает n, n целых чисел, q и q пар индексов l r. Для каждого запроса печатает
     * на отдельной строке сумму a[l] + … + a[r]. Должно выполняться 0 <= l <= r < n.
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

            // Массив сам не храним — сразу строим префиксные суммы:
            // p[i] = a[0] + … + a[i-1], p[0] = 0. Суммы в long: до n · 2^31
            long[] p = new long[n + 1];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                p[i + 1] = p[i] + scanner.nextInt();
            }

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int q = scanner.nextInt();
            if (q < MIN_QUERIES || q > MAX_QUERIES) {
                System.out.println("Ошибка: некорректное количество запросов");
                return;
            }

            // Ответы копим и печатаем в конце, чтобы при ошибке в запросе не было частичного вывода
            long[] s = new long[q];
            for (int k = 0; k < q; k++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                int l = scanner.nextInt();
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                int r = scanner.nextInt();
                if (l < 0 || r >= n || l > r) {
                    System.out.println("Ошибка: некорректный запрос");
                    return;
                }
                // Сумма a[l..r] = (a[0..r]) - (a[0..l-1]) — одно вычитание
                s[k] = p[r + 1] - p[l];
            }

            for (int k = 0; k < q; k++) {
                System.out.println(s[k]);
            }
        }
    }
}
