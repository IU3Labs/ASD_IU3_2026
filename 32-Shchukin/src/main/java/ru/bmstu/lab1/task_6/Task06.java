/*
 * Задача №6. Элементы больше среднего.
 * Подсчитать элементы строго больше среднего арифметического массива.
 */

package ru.bmstu.lab1.task_6;

import ru.bmstu.lab1.InputReader;

/**
 * Решение задачи №6: «Элементы больше среднего».
 */
public final class Task06 {
    private Task06() {
    }

    /** Считает элементы строго больше среднего; среднее по условию целое. */
    public static int countAboveAverage(int[] values) {
        long sum = 0;
        for (int value : values) {
            sum += value;
        }
        long average = sum / values.length;
        int count = 0;
        for (int value : values) {
            if (value > average) {
                count++;
            }
        }
        return count;
    }

    /** Считывает массив и выводит число элементов больше среднего. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int[] values = input.nextArray(input.nextInt());
        System.out.println(countAboveAverage(values));
    }
}
