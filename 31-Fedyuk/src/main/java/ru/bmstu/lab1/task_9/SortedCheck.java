/*
 * Задача 9. Проверка отсортированности.
 * Вход: массив из n целых чисел.
 * Выход: YES, если массив неубывающий; NO иначе.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: базовая
 */
package ru.bmstu.lab1.task_9;

import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 9. Проверка отсортированности: вывести YES для неубывающего массива, иначе NO. */
public final class SortedCheck {
    private SortedCheck() {}

    /**
     * Проверяет неубывание; равные соседние элементы допустимы.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @return результат решения задачи
     */
    public static boolean solve(int[] values) {
        for (int index = 1; index < values.length; index++) {
            if (values[index] < values[index - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            System.out.println(solve(values) ? "YES" : "NO");
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
