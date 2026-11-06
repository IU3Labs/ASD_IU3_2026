package ru.bmstu.lab1.task_8;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 8. Разделить положительные и отрицательные элементы, исключив нули. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int[][] solve(int[] values) {
        int positiveSize = 0;
        int negativeSize = 0;
        for (int number : values) {
            if (number > 0) {
                positiveSize++;
            } else if (number < 0) {
                negativeSize++;
            }
        }
        int[] positiveValues = new int[positiveSize];
        int[] negativeValues = new int[negativeSize];
        fillGroups(values, positiveValues, negativeValues);
        return new int[][] {positiveValues, negativeValues};
    }

    private static void fillGroups(int[] values, int[] positiveValues, int[] negativeValues) {
        int nextPositive = 0;
        int nextNegative = 0;
        for (int number : values) {
            if (number > 0) {
                positiveValues[nextPositive++] = number;
            } else if (number < 0) {
                negativeValues[nextNegative++] = number;
            }
        }
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        int[][] result = solve(values);
        System.out.println(ConsoleInput.format(result[0]));
        System.out.println(ConsoleInput.format(result[1]));
    }
}
