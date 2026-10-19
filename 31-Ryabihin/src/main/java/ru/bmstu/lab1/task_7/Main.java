/*
 * Задача 7. Второй максимум.
 * Вход: массив из n целых чисел, все элементы различны.
 * Выход: второй по величине элемент массива.
 */
package ru.bmstu.lab1.task_7;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputHelper;

/** Запуск задачи 7. Ввод: n, затем n различных чисел. Вывод: второй максимум. */
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
        out.println(SecondMax.find(array));
    }
}
