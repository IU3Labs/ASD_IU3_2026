/*
 * Задача 34. Максимальная сумма подмассива.
 * Вход: массив из n целых чисел (могут быть отрицательные).
 * Выход: максимальная сумма непрерывного подмассива. Требование: O(n), алгоритм Кадане.
 */
package ru.bmstu.lab1.task_34;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputHelper;

/** Запуск задачи 34. Ввод: n, затем n чисел. Вывод: максимальная сумма. */
public final class Main {

    private Main() {}

    /**
     * Точка входа.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }

    /**
     * Читает данные, решает задачу и печатает ответ.
     *
     * @param scanner откуда читать
     * @param out куда печатать
     */
    public static void run(Scanner scanner, PrintStream out) {
        int[] array = InputHelper.readArray(scanner);
        out.println(MaxSubarray.maxSum(array));
    }
}
