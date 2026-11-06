/*
 * Задача 6. Элементы больше среднего.
 * Вход: массив из n целых чисел.
 * Выход: количество элементов, строго больших среднего арифметического всех элементов. Гарантируется,
 * что среднее — целое число (сумма делится на n).
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_6;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 6. Элементы больше среднего: посчитать элементы строго больше целого среднего. */
public final class AboveAverageCount {
    private AboveAverageCount() {}

    /**
     * Возвращает количество элементов выше среднего; сумма должна делиться на n.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
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

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            System.out.println(solve(values));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
