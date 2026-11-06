/*
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_1;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 1. Максимум и минимум: найти минимальный и максимальный элементы массива. */
public final class MinMax {
    private MinMax() {}

    /**
     * Возвращает массив из двух чисел: минимума, затем максимума.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
    public static int[] solve(int[] values) {
        int minimum = values[0];
        int maximum = values[0];
        for (int index = 1; index < values.length; index++) {
            minimum = Math.min(minimum, values[index]);
            maximum = Math.max(maximum, values[index]);
        }
        return new int[] {minimum, maximum};
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            System.out.println(ConsoleIo.format(solve(values)));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
