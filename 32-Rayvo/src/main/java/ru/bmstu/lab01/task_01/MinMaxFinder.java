package ru.bmstu.lab01.task_01;

public class MinMaxFinder {

    /**
     * Результат поиска: минимальный и максимальный элементы.
     *
     * @param min минимальный элемент
     * @param max максимальный элемент
     */
    public record MinMax(int min, int max) {
    }

    /**
     * Возвращает минимальный и максимальный элементы массива.
     *
     * @param array входной массив целых чисел
     * @return пара {@code (min, max)}
     * @throws IllegalArgumentException если массив {@code null} или пуст
     */
    public static MinMax findMinMax(int[] array) {

        int min = array[0];
        int max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > max) {
                max = array[i];
            }
        }

        return new MinMax(min, max);
    }
}