package ru.bmstu.lab01.task_25;

public class SumOfElements {

    /**
     * Возвращает сумму всех элементов массива.
     *
     * <p>Сумма накапливается в {@code long}, чтобы избежать переполнения
     * {@code int} при больших значениях элементов.
     *
     * @param array входной массив целых чисел
     * @return сумма всех элементов
     */
    public static long sum(int[] array) {

        long sum = 0L;
        for (int value : array) {
            sum += value;
        }

        return sum;
    }
}