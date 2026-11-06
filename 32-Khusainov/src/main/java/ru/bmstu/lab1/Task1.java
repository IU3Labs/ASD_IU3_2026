package ru.bmstu.lab1;

/**
 * Решение задачи 1: поиск минимального и максимального элементов массива.
 */
public class Task1 {

    /**
     * Находит минимальный и максимальный элементы в массиве.
     *
     * @param array входной массив целых чисел (по условию 1 < array.length() < 100000)
     * @return массив из двух элементов: минимум и максимум
     */
    public static int[] findMinMax(int[] array) {
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

        return new int[] {min, max};
    }
}