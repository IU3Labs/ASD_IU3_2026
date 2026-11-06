package ru.bmstu.lab01.task_27;

public class SumOfSquares {

    /**
     * Возвращает сумму квадратов всех элементов массива.
     *
     * <p>Квадрат каждого элемента вычисляется как
     * {@code (long) value * value}, поэтому переполнение {@code int}
     * на промежуточном шаге невозможно.
     *
     * @param array входной массив целых чисел
     * @return сумма квадратов всех элементов
     */
    public static long sumOfSquares(int[] array) {
        long sum = 0L;
        for (int value : array) {
            sum += (long) value * value;
        }

        return sum;
    }
}