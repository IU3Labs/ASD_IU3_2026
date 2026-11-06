package ru.bmstu.lab1.task_10;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 10. Найти индекс последнего элемента, кратного ненулевому k. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int solve(int[] values, int k) {
        for (int i = values.length - 1; i >= 0; i--) {
            if (values[i] % k == 0) {
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
