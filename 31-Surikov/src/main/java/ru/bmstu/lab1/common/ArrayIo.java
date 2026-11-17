package ru.bmstu.lab1.common;

import java.io.PrintWriter;
import java.util.Scanner;

/** чтение массива с размером и вывод элементов без служебных сообщений */
public final class ArrayIo {
    private ArrayIo() {
    }

    /** читает неотрицательный размер и соответствующее количество целых чисел */
    public static int[] readArray(Scanner input) {
        int size = input.nextInt();
        if (size < 0) {
            throw new IllegalArgumentException("Размер массива не может быть отрицательным");
        }
        int[] values = new int[size];
        for (int index = 0; index < size; index++) {
            values[index] = input.nextInt();
        }
        return values;
    }

    /** выводит массив одной строкой; пустой массив представлен пустой строкой */
    public static void printArray(PrintWriter output, int[] values) {
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                output.print(' ');
            }
            output.print(values[index]);
        }
        output.println();
        output.flush();
    }
}
