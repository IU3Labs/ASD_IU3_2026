package ru.bmstu.lab1.task_6;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 6. Подсчитать элементы, строго большие целого среднего арифметического. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int solve(int[] values) {
        long sum = 0;
        for (int value : values) {
            sum += value;
        }
        long average = sum / values.length;
        int count = 0;
        for (int value : values) {
            if (value > average) {
                count++;
            }
        }
        return count;
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values));
    }
}
