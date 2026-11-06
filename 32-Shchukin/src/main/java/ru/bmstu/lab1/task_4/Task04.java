/*
 * Задача №4. Все индексы элемента.
 * Вывести все индексы вхождений числа x в порядке возрастания.
 */

package ru.bmstu.lab1.task_4;

import ru.bmstu.lab1.InputReader;
import ru.bmstu.lab1.OutputFormatter;

/**
 * Решение задачи №4: «Все индексы элемента».
 */
public final class Task04 {
    private Task04() {
    }

    /** Возвращает все индексы совпадений, не изменяя исходный массив. */
    public static int[] allIndices(int[] values, int target) {
        int matches = 0;
        for (int value : values) {
            if (value == target) {
                matches++;
            }
        }
        int[] indices = new int[matches];
        int next = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == target) {
                indices[next++] = i;
            }
        }
        return indices;
    }

    /** Считывает массив и число x, выводит все найденные индексы. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int[] values = input.nextArray(input.nextInt());
        System.out.println(OutputFormatter.format(allIndices(values, input.nextInt())));
    }
}
