/*
 * Задача №1. Максимум и минимум.
 * Найти минимальный и максимальный элементы массива.
 */

package ru.bmstu.lab1.task_1;

import ru.bmstu.lab1.InputReader;
import ru.bmstu.lab1.OutputFormatter;

/**
 * Решение задачи №1: «Максимум и минимум».
 */
public final class Task01 {
    private Task01() {
    }

    /** Возвращает минимум и максимум массива в указанном порядке. */
    public static int[] findMinMax(int[] values) {
        int minimum = values[0];
        int maximum = values[0];
        for (int value : values) {
            minimum = Math.min(minimum, value);
            maximum = Math.max(maximum, value);
        }
        return new int[]{minimum, maximum};
    }

    /** Считывает размер и элементы массива, выводит минимум и максимум. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int[] values = input.nextArray(input.nextInt());
        System.out.println(OutputFormatter.format(findMinMax(values)));
    }
}
