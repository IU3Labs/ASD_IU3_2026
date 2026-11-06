/*
 * Задача 37. Разворот массива.
 * Вход: массив из n целых чисел.
 * Выход: массив, записанный в обратном порядке.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Требование: выполнить разворот на месте, без создания нового массива.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_37;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayReversal;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 37. Разворот массива: изменить порядок элементов на обратный без нового массива. */
public final class ArrayReverse {
    private ArrayReverse() {}

    /**
     * Разворачивает переданный массив на месте за O(n) времени и O(1) дополнительной памяти.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     */
    public static void solve(int[] values) {
        ArrayReversal.reverse(values, 0, values.length);
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            solve(values);
            System.out.println(ConsoleIo.format(values));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
