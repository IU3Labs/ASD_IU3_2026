package ru.bmstu.lab1.task_9;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 9. Проверить, является ли массив неубывающим. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static String solve(int[] values) {
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[i - 1]) {
                return "NO";
            }
        }
        return "YES";
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values));
    }
}
