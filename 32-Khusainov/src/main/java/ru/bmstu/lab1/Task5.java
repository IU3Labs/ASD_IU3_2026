package ru.bmstu.lab1;

/**
 * Решение задачи 5: сумма элементов на чётных позициях.
 */
public class Task5 {

    /**
     * Вычисляет сумму элементов массива, стоящих на чётных индексах (0, 2, 4, ...).
     * Возвращает long, так как сумма может превысить Integer.MAX_VALUE.
     *
     * @param array входной массив целых чисел
     * @return сумма элементов с чётными индексами
     */
    public static long sumEvenIndices(int[] array) {
        long sum = 0;

        for (int i = 0; i < array.length; i += 2) {
            sum += array[i];
        }

        return sum;
    }
}