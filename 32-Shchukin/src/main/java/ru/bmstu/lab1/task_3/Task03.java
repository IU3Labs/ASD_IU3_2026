/*
 * Задача №3. Подсчёт вхождений.
 * Подсчитать количество элементов массива, равных числу x.
 */

package ru.bmstu.lab1.task_3;

import ru.bmstu.lab1.InputReader;

/**
 * Решение задачи №3: «Подсчёт вхождений».
 */
public final class Task03 {
    private Task03() {
    }

    /** Подсчитывает количество вхождений заданного числа. */
    public static int countOccurrences(int[] values, int target) {
        int count = 0;
        for (int value : values) {
            if (value == target) {
                count++;
            }
        }
        return count;
    }

    /** Считывает массив и число x, выводит число вхождений. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int[] values = input.nextArray(input.nextInt());
        System.out.println(countOccurrences(values, input.nextInt()));
    }
}
