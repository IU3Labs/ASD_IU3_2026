/*
 * Задача 34. Максимальная сумма подмассива.
 * Вход: массив из n целых чисел (могут быть отрицательные).
 * Выход: максимальная сумма непрерывного подмассива. Требование: O(n), алгоритм Кадане.
 */
package ru.bmstu.lab1.task_34;

/** Максимальная сумма подмассива, алгоритм Кадане. */
public final class MaxSubarray {

    private MaxSubarray() {}

    /**
     * Находит максимальную сумму непрерывного подмассива.
     *
     * <p>current - лучшая сумма подмассива, который заканчивается на текущем элементе. Его либо
     * продолжаем текущим элементом, либо начинаем заново с него. Ответ - максимум из всех current.
     * Если все числа отрицательные, ответ - самое большое из них.
     *
     * @param array непустой массив
     * @return максимальная сумма
     */
    public static long maxSum(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Массив пустой");
        }
        long current = array[0];
        long best = array[0];
        for (int i = 1; i < array.length; i++) {
            current = Math.max(array[i], current + array[i]);
            best = Math.max(best, current);
        }
        return best;
    }
}
