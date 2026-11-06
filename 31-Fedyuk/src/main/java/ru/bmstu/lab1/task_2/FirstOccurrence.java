/*
 * Задача 2. Индекс первого вхождения.
 * Вход: массив из n целых чисел и число x.
 * Выход: индекс первого элемента, равного x. Если такого нет — вывести -1.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_2;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 2. Индекс первого вхождения: найти первый индекс числа x или вернуть -1. */
public final class FirstOccurrence {
    private FirstOccurrence() {}

    /**
     * Возвращает первый индекс искомого значения или -1, если его нет.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @param target искомое значение
     * @return результат решения задачи
     */
    public static int solve(int[] values, int target) {
        for (int index = 0; index < values.length; index++) {
            if (values[index] == target) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            System.out.println(solve(values, ConsoleIo.readInt(scanner, "Введите x")));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
