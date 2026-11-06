package ru.bmstu.lab1.task_7;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 7. Найти второй максимум массива из различных чисел. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int solve(int[] values) {
        int largestValue = Math.max(values[0], values[1]);
        int secondLargestValue = Math.min(values[0], values[1]);
        for (int i = 2; i < values.length; i++) {
            int currentValue = values[i];
            if (currentValue > largestValue) {
                secondLargestValue = largestValue;
                largestValue = currentValue;
            } else if (currentValue > secondLargestValue) {
                secondLargestValue = currentValue;
            }
        }
        return secondLargestValue;
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values));
    }
}
