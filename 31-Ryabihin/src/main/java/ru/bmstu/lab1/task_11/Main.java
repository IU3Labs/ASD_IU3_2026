/*
 * Задача 11. Ближайший к среднему.
 * Вход: массив из n целых чисел.
 * Выход: элемент, ближайший к среднему арифметическому (если несколько - первый по индексу).
 * Среднее может быть дробным.
 */
package ru.bmstu.lab1.task_11;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputHelper;

/** Запуск задачи 11. Ввод: n, затем n чисел. Вывод: ближайший к среднему элемент. */
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
        out.println(ClosestToAverage.find(array));
    }
}
