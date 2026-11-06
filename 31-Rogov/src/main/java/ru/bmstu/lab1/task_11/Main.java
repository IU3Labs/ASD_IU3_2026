package ru.bmstu.lab1.task_11;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 11. Найти ближайший к среднему элемент; при равенстве выбрать первый. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int solve(int[] values) {
        long total = 0;
        for (int value : values) {
            total += value;
        }
        int nearestIndex = 0;
        long minimumDistance = Long.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            // Умножаем расстояние на n: сравниваем точно, без ошибок double.
            long currentDistance = Math.abs((long) values[i] * values.length - total);
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearestIndex = i;
            }
        }
        return values[nearestIndex];
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values));
    }
}
