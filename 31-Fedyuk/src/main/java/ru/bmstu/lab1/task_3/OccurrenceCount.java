/*
 * Задача 3. Подсчёт вхождений.
 * Вход: массив из n целых чисел и число x.
 * Выход: количество элементов, равных x.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_3;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 3. Подсчёт вхождений: определить количество элементов, равных x. */
public final class OccurrenceCount {
    private OccurrenceCount() {}

    /**
     * Возвращает количество вхождений искомого значения.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @param target искомое значение
     * @return результат решения задачи
     */
    public static int solve(int[] values, int target) {
        int count = 0;
        for (int value : values) {
            if (value == target) {
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
            System.out.println(solve(values, ConsoleIo.readInt(scanner, "Введите x")));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
