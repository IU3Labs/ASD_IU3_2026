/*
 * Задача 36. Бегущая сумма.
 * Вход: массив из n целых чисел.
 * Выход: массив той же длины, где i-й элемент равен сумме первых i + 1 элементов исходного массива.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_36;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 36. Бегущая сумма: на позиции i записать сумму элементов с индексами от 0 до i. */
public final class RunningSum {
    private RunningSum() {}

    /**
     * Возвращает накопленные суммы в новом long-массиве, сохраняя исходные данные.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
    public static long[] solve(int[] values) {
        long[] sums = new long[values.length];
        long total = 0;
        for (int index = 0; index < values.length; index++) {
            total += values[index];
            sums[index] = total;
        }
        return sums;
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
