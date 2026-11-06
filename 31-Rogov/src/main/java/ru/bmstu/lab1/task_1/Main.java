package ru.bmstu.lab1.task_1;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 1. Найти минимальный и максимальный элементы массива. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int[] solve(int[] values) {
        int minimum = values[0];
        int maximum = values[0];
        for (int value : values) {
            minimum = Math.min(minimum, value);
            maximum = Math.max(maximum, value);
        }
        return new int[] {minimum, maximum};
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(ConsoleInput.format(solve(values)));
    }
}
