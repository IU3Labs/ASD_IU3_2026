/*
 * Задача 4. Все индексы элемента.
 * Вход: массив из n целых чисел и число x.
 * Выход: все индексы элементов, равных x, в порядке возрастания.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_4;

import java.util.Arrays;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 4. Все индексы элемента: вывести индексы вхождений x в порядке возрастания. */
public final class AllIndices {
    private AllIndices() {}

    /**
     * Возвращает индексы совпадений, сохраняя порядок исходного массива.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @param target искомое значение
     * @return результат решения задачи
     */
    public static int[] solve(int[] values, int target) {
        int[] indices = new int[values.length];
        int count = 0;
        for (int index = 0; index < values.length; index++) {
            if (values[index] == target) {
                indices[count++] = index;
            }
        }
        return Arrays.copyOf(indices, count);
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            System.out.println(
                    ConsoleIo.format(solve(values, ConsoleIo.readInt(scanner, "Введите x"))));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
