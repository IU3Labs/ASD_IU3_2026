package ru.bmstu.lab1.task01;

/**
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 */
public class MinMaxFinder {

    private MinMaxFinder() {
    }

    /**
     * Находит минимум и максимум массива за один проход.
     * Начальные значения берём из первого элемента, поэтому
     * правильно работает даже для Integer.MIN_VALUE и Integer.MAX_VALUE.
     *
     * @param arr массив чисел (не пустой)
     * @return массив из двух чисел: {минимум, максимум}
     */
    public static int[] findMinMax(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }
        int min = arr[0];
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return new int[] {min, max};
    }
}
