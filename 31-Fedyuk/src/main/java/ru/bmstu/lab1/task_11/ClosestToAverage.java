/*
 * Задача 11. Ближайший к среднему.
 * Вход: массив из n целых чисел.
 * Выход: элемент массива, ближайший к среднему арифметическому. Если несколько — вывести первый по
 * индексу.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Ограничения: среднее может быть дробным.
 * Сложность: средняя
 */
package ru.bmstu.lab1.task_11;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 11. Ближайший к среднему: при равных расстояниях выбрать первый по индексу элемент. */
public final class ClosestToAverage {
    private ClosestToAverage() {}

    /**
     * Возвращает ближайший к среднему элемент без округления дробного среднего.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
    public static int solve(int[] values) {
        long sum = 0;
        for (int value : values) {
            sum += value;
        }
        int closest = values[0];
        // |a - sum/n| сравниваем как |a*n - sum|: общий множитель n положителен.
        long bestDistance = Math.abs((long) closest * values.length - sum);
        for (int index = 1; index < values.length; index++) {
            long distance = Math.abs((long) values[index] * values.length - sum);
            if (distance < bestDistance) {
                bestDistance = distance;
                closest = values[index];
            }
        }
        return closest;
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
