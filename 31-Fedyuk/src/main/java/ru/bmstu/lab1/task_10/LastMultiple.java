/*
 * Задача 10. Поиск элемента с условием.
 * Вход: массив из n целых чисел и число k.
 * Выход: индекс последнего элемента, кратного k. Если такого нет — вывести -1.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Ограничения: k != 0.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_10;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 10. Поиск элемента с условием: найти последний индекс элемента, кратного ненулевому k. */
public final class LastMultiple {
    private LastMultiple() {}

    /**
     * Возвращает последний индекс кратного делителю элемента или -1.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @param divisor ненулевой делитель
     * @return результат решения задачи
     */
    public static int solve(int[] values, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("Делитель не должен быть равен нулю");
        }
        for (int index = values.length - 1; index >= 0; index--) {
            if (values[index] % divisor == 0) {
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
            System.out.println(solve(values, ConsoleIo.readInt(scanner, "Введите k")));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
