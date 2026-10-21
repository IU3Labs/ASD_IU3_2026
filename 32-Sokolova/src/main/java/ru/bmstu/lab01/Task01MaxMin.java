package ru.bmstu.lab01;

import java.io.IOException;

/**
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 * Ограничения: 1 < n < 10^5.
 * Сложность: O(n) по времени, O(1) по дополнительной памяти.
 */
public class Task01MaxMin {

    /**
     * Находит минимальный и максимальный элементы массива за один проход.
     *
     * @param numbers входной массив
     * @return массив вида [min, max]
     */
    public static int[] findMinMax(int[] numbers) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : numbers) {
            if (num < min) {
                min = num;
            }
            if (num > max) {
                max = num;
            }
        }
        return new int[]{min, max};
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] numbers = StdIn.readArray(n);
        int[] result = findMinMax(numbers);
        System.out.println(result[0] + " " + result[1]);
    }
}