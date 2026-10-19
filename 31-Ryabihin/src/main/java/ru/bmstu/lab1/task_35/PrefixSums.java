/*
 * Задача 35. Префиксные суммы.
 * Вход: массив из n целых чисел и q запросов [l, r].
 * Выход: для каждого запроса сумма элементов с l по r включительно.
 * Требование: предпосчёт префиксных сумм, затем O(1) на запрос.
 */
package ru.bmstu.lab1.task_35;

/** Префиксные суммы для быстрых запросов суммы на отрезке. */
public final class PrefixSums {

    // prefix[i] - сумма первых i элементов, prefix[0] = 0
    private final long[] prefix;

    /**
     * Считает префиксные суммы за O(n).
     *
     * @param array исходный массив
     */
    public PrefixSums(int[] array) {
        prefix = new long[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            prefix[i + 1] = prefix[i] + array[i];
        }
    }

    /**
     * Сумма элементов с left по right включительно за O(1).
     *
     * @param left левая граница
     * @param right правая граница
     * @return сумма на отрезке
     */
    public long sum(int left, int right) {
        if (left < 0 || right >= prefix.length - 1 || left > right) {
            throw new IllegalArgumentException("Неверный отрезок: " + left + " " + right);
        }
        return prefix[right + 1] - prefix[left];
    }
}
