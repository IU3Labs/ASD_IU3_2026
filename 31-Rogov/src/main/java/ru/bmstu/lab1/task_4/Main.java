package ru.bmstu.lab1.task_4;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 4. Вывести все индексы вхождений x по возрастанию. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int[] solve(int[] values, int x) {
        int count = 0;
        for (int value : values) {
            if (value == x) {
                count++;
            }
        }
        int[] indexes = new int[count];
        int position = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == x) {
                indexes[position++] = i;
            }
        }
        return indexes;
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(ConsoleInput.format(solve(values, input.readInt())));
    }
}
