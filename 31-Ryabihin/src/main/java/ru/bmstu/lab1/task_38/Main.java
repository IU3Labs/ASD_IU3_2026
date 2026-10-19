/*
 * Задача 38. Циклический сдвиг влево.
 * Вход: массив из n целых чисел и число k.
 * Выход: массив, сдвинутый влево на k позиций.
 * Требование: O(n) времени, O(1) доп. памяти (через тройной разворот).
 */
package ru.bmstu.lab1.task_38;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputHelper;
import ru.bmstu.lab1.common.OutputHelper;

/** Запуск задачи 38. Ввод: n, затем n чисел, затем k. Вывод: сдвинутый массив. */
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
        int k = scanner.nextInt();
        RotateLeft.rotate(array, k);
        out.println(OutputHelper.join(array));
    }
}
