// задача 45 — разбиение массива по опорному элементу
package ru.bmstu.lab1.task_45;

import java.io.PrintWriter;
import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;
import ru.bmstu.lab1.common.ArrayOperations;

/** разбиение массива на три группы относительно опорного значения на месте */
public final class Main {
    private Main() {
    }

    /** переставляет элементы на месте; порядок внутри групп может измениться */
    public static void partition(int[] values, int pivot) {
        int lessEnd = 0;
        int current = 0;
        int greaterStart = values.length;

        while (current < greaterStart) {
            if (values[current] < pivot) {
                ArrayOperations.swap(values, lessEnd++, current++);
            } else if (values[current] > pivot) {
                // элемент справа ещё не проверен, поэтому current не увеличиваем
                ArrayOperations.swap(values, current, --greaterStart);
            } else {
                current++;
            }
        }
    }

    /** читает входные данные и выводит ответ */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);
        int[] values = ArrayIo.readArray(input);
        int pivot = input.nextInt();
        partition(values, pivot);
        ArrayIo.printArray(output, values);
    }
}
