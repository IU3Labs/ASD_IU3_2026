/*
 * Задача 19. Объединение двух отсортированных массивов.
 * Вход: два массива, отсортированных по неубыванию, размеры n и m.
 * Выход: один отсортированный массив. Требование: O(n + m).
 */
package ru.bmstu.lab1.task_19;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputHelper;
import ru.bmstu.lab1.common.OutputHelper;

/**
 * Запуск задачи 19. Ввод: n и n чисел первого массива, затем m и m чисел второго. Вывод: слитый
 * массив.
 */
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
        int[] a = InputHelper.readArray(scanner);
        int[] b = InputHelper.readArray(scanner);
        out.println(OutputHelper.join(MergeSorted.merge(a, b)));
    }
}
