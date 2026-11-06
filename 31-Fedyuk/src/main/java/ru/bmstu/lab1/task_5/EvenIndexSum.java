/*
 * Задача 5. Сумма элементов на чётных позициях.
 * Вход: массив из n целых чисел.
 * Выход: сумма элементов с чётными индексами (0, 2, 4, …).
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_5;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 5. Сумма элементов на чётных позициях: сложить элементы с индексами 0, 2, 4, ... */
public final class EvenIndexSum {
    private EvenIndexSum() {}

    private static final int INDEX_STEP = 2;

    /**
     * Возвращает сумму на чётных индексах; long предотвращает переполнение int.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
    public static long solve(int[] values) {
        long sum = 0;
        for (int index = 0; index < values.length; index += INDEX_STEP) {
            sum += values[index];
        }
        return sum;
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
