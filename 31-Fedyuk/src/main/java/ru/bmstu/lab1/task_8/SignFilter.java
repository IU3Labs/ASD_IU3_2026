/*
 * Задача 8. Фильтрация по знаку.
 * Вход: массив из n целых чисел.
 * Выход: два массива — из положительных и из отрицательных элементов, в исходном порядке. Нули не
 * включать ни в один.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_8;

import java.util.Arrays;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 8. Фильтрация по знаку: выделить положительные и отрицательные числа, исключая нули. */
public final class SignFilter {
    private SignFilter() {}

    /**
     * Возвращает положительные и отрицательные элементы в двух массивах, сохраняя порядок.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
    public static int[][] solve(int[] values) {
        int[] positives = new int[values.length];
        int[] negatives = new int[values.length];
        int positiveCount = 0;
        int negativeCount = 0;
        for (int value : values) {
            if (value > 0) {
                positives[positiveCount++] = value;
            } else if (value < 0) {
                negatives[negativeCount++] = value;
            }
        }
        return new int[][] {
            Arrays.copyOf(positives, positiveCount), Arrays.copyOf(negatives, negativeCount)
        };
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            int[][] result = solve(values);
            System.out.println(ConsoleIo.format(result[0]));
            System.out.println(ConsoleIo.format(result[1]));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
