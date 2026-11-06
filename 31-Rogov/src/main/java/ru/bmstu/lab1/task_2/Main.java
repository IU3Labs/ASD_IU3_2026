package ru.bmstu.lab1.task_2;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 2. Найти индекс первого вхождения x или -1. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int solve(int[] values, int x) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == x) {
                return i;
            }
        }
        return -1;
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values, input.readInt()));
    }
}
