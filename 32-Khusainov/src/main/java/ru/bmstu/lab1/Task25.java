package ru.bmstu.lab1;

/**
 * Решение задачи 25: сумма всех элементов массива.
 */
public class Task25 {

    /**
     * Вычисляет сумму всех элементов массива.
     * Возвращает long, так как сумма может превысить Integer.MAX_VALUE.
     *
     * @param array входной массив целых чисел
     * @return сумма всех элементов
     */
    public static long sumAll(int[] array) {
        long sum = 0;

        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }

        return sum;
    }
}