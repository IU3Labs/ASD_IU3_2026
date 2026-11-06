/*
 * Задача №5. Сумма элементов на чётных позициях.
 * Найти сумму элементов массива с чётными индексами (0, 2, 4, …).
 */

package ru.bmstu.lab1.task_5;

import ru.bmstu.lab1.InputReader;

/**
 * Решение задачи №5: «Сумма элементов на чётных позициях».
 */
public final class Task05 {
    private Task05() {
    }

    /** Вычисляет сумму элементов с индексами 0, 2, 4 и так далее. */
    public static long sumEvenPositions(int[] values) {
        long sum = 0;
        for (int i = 0; i < values.length; i += 2) {
            sum += values[i];
        }
        return sum;
    }

    /** Считывает массив и выводит сумму элементов на чётных позициях. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int[] values = input.nextArray(input.nextInt());
        System.out.println(sumEvenPositions(values));
    }
}
