/*
 * Задача 41. Удаление элемента.
 * Вход: массив из n целых чисел и число x.
 * Выход: массив, из которого удалены все вхождения x.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_41;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 41. Удаление элемента: удалить все вхождения x, сохранив порядок остальных чисел. */
public final class RemoveElement {
    private RemoveElement() {}

    /**
     * Возвращает новый массив без искомого значения; исходный массив не изменяется.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @param target искомое значение
     * @return результат решения задачи
     */
    public static int[] solve(int[] values, int target) {
        int remainingCount = 0;
        for (int value : values) {
            if (value != target) {
                remainingCount++;
            }
        }
        int[] remaining = new int[remainingCount];
        int nextIndex = 0;
        for (int value : values) {
            if (value != target) {
                remaining[nextIndex++] = value;
            }
        }
        return remaining;
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            System.out.println(
                    ConsoleIo.format(solve(values, ConsoleIo.readInt(scanner, "Введите x"))));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
