package ru.bmstu.lab01.task_05;

public class EvenIndexSum {

    /**
     * Возвращает сумму элементов массива с чётными индексами.
     *
     * @param array входной массив целых чисел
     * @return сумма элементов с индексами {@code 0, 2, 4, ...}
     */
    public static long sumEvenIndices(int[] array) {

        long sum = 0L;
        for (int i = 0; i < array.length; i += 2) {
            sum += array[i];
        }

        return sum;
    }
}