/*
 * Задача №41. Удаление элемента.
 * Удалить из массива все вхождения числа x, сохранив порядок остальных.
 */

package ru.bmstu.lab1.task_41;

import ru.bmstu.lab1.InputReader;
import ru.bmstu.lab1.OutputFormatter;

/**
 * Решение задачи №41: «Удаление элемента».
 */
public final class Task41 {
    private Task41() {
    }

    /** Создаёт массив без заданного числа с сохранением порядка элементов. */
    public static int[] removeAll(int[] values, int target) {
        int kept = 0;
        for (int value : values) {
            if (value != target) {
                kept++;
            }
        }
        int[] result = new int[kept];
        int next = 0;
        for (int value : values) {
            if (value != target) {
                result[next++] = value;
            }
        }
        return result;
    }

    /** Считывает массив и число x, выводит оставшиеся элементы. */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int[] values = input.nextArray(input.nextInt());
        System.out.println(OutputFormatter.format(removeAll(values, input.nextInt())));
    }
}
