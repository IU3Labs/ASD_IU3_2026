/*
 * Задача №11. Ближайший к среднему.
 * Найти ближайший к среднему элемент; при равенстве выбрать первый по индексу.
 */

package ru.bmstu.lab1.task_11;

import ru.bmstu.lab1.InputReader;

/**
 * Решение задачи №11: «Ближайший к среднему».
 */
public final class Task11 {
    private Task11() {
    }

    /** Возвращает ближайшее к среднему значение; при равенстве сохраняет первое. */
    public static int closestToAverage(int[] values) {
        long sum = 0;
        for (int value : values) {
            sum += value;
        }
        // Сравнение без дробных чисел: abs(value * n - sum) исключает ошибки округления.
        long bestDistance = Long.MAX_VALUE;
        int closest = values[0];
        for (int value : values) {
            long distance = Math.abs((long) value * values.length - sum);
            if (distance < bestDistance) {
                bestDistance = distance;
                closest = value;
            }
        }
        return closest;
    }

    /** Считывает массив и выводит ближайший к среднему элемент. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int[] values = input.nextArray(input.nextInt());
        System.out.println(closestToAverage(values));
    }
}
