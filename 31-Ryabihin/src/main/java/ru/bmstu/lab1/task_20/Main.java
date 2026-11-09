/*
 * Задача 20. Сортировка подсчётом.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию, с помощью сортировки подсчётом.
 */
package ru.bmstu.lab1.task_20;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputHelper;
import ru.bmstu.lab1.common.OutputHelper;

/** Запуск задачи 20. Ввод: n, затем n чисел. Вывод: отсортированный массив. */
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
        out.println(OutputHelper.join(CountingSort.sort(array)));
    }
}
