package ru.bmstu.lab1.task_3;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 3. Подсчитать количество вхождений x. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int solve(int[] values, int x) {
        int count = 0;
        for (int value : values) {
            if (value == x) {
                count++;
            }
        }
        return count;
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values, input.readInt()));
    }
}
