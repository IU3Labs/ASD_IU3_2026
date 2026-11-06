/*
 * Задача 26. Произведение элементов.
 * Вход: массив из n целых чисел.
 * Выход: произведение всех элементов по модулю 10^9 + 7.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_26;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 26. Произведение элементов: вычислить произведение по модулю 10^9 + 7. */
public final class ModularProduct {
    private ModularProduct() {}

    private static final long MODULUS = 1_000_000_007L;

    /**
     * Возвращает неотрицательный остаток произведения, в том числе для отрицательных чисел.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
    public static long solve(int[] values) {
        long product = 1;
        for (int value : values) {
            // Оба множителя меньше MODULUS, поэтому их произведение помещается в long.
            long remainder = Math.floorMod((long) value, MODULUS);
            product = product * remainder % MODULUS;
        }
        return product;
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
