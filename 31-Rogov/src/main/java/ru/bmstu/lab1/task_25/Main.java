package ru.bmstu.lab1.task_25;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 25. Найти сумму всех элементов массива. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static long solve(int[] values) {
        long total = 0;
        for (int number : values) {
            total += number;
        }
        return total;
    }

    /** Читает n и n целых чисел, затем печатает результат. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values));
    }
}
