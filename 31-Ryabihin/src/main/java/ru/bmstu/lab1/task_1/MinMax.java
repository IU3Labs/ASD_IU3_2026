/*
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 */
package ru.bmstu.lab1.task_1;

/** Поиск минимума и максимума за один проход по массиву. */
public final class MinMax {

    private MinMax() {}

    /**
     * Находит минимум и максимум массива.
     *
     * @param array непустой массив
     * @return массив из двух чисел: {минимум, максимум}
     */
    public static int[] findMinMax(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Массив пустой");
        }
        // начинаем с первого элемента, остальные сравниваем с ним
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
