/*
 * Задача 45. Разбиение по опорному элементу.
 * Вход: массив из n целых чисел и опорное значение p.
 * Выход: сначала элементы < p, потом = p, потом > p (порядок внутри групп не важен).
 * Требование: O(n) времени, O(1) доп. памяти.
 */
package ru.bmstu.lab1.task_45;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputHelper;
import ru.bmstu.lab1.common.OutputHelper;

/** Запуск задачи 45. Ввод: n, затем n чисел, затем p. Вывод: переставленный массив. */
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
        int pivot = scanner.nextInt();
        Partition.partition(array, pivot);
        out.println(OutputHelper.join(array));
    }
}
