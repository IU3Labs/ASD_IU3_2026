/*
 * Задача №2. Индекс первого вхождения.
 * Найти индекс первого вхождения числа x; если его нет, вывести -1.
 */

package ru.bmstu.lab1.task_2;

import ru.bmstu.lab1.InputReader;

/**
 * Решение задачи №2: «Индекс первого вхождения».
 */
public final class Task02 {
    private Task02() {
    }

    /** Находит первый индекс заданного числа или -1, если его нет. */
    public static int firstIndex(int[] values, int target) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /** Считывает массив и число x, выводит первый индекс или -1. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int[] values = input.nextArray(input.nextInt());
        System.out.println(firstIndex(values, input.nextInt()));
    }
}
