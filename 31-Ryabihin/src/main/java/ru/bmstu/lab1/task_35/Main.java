/*
 * Задача 35. Префиксные суммы.
 * Вход: массив из n целых чисел и q запросов [l, r].
 * Выход: для каждого запроса сумма элементов с l по r включительно.
 * Требование: предпосчёт префиксных сумм, затем O(1) на запрос.
 */
package ru.bmstu.lab1.task_35;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputHelper;

/** Запуск задачи 35. Ввод: n и n чисел, затем q и q пар l r. Вывод: q строк с суммами. */
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
        PrefixSums sums = new PrefixSums(InputHelper.readArray(scanner));
        int q = scanner.nextInt();
        for (int i = 0; i < q; i++) {
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            out.println(sums.sum(left, right));
        }
    }
}
