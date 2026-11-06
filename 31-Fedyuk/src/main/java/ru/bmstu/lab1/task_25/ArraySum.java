/*
 * Задача 25. Сумма элементов.
 * Вход: массив из n целых чисел.
 * Выход: сумма всех элементов.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_25;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 25. Сумма элементов: найти сумму всех чисел массива. */
public final class ArraySum {
    private ArraySum() {}

    /**
     * Возвращает сумму в long, чтобы избежать переполнения int.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
    public static long solve(int[] values) {
        long total = 0;
        for (int value : values) {
            total += value;
        }
        return total;
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
