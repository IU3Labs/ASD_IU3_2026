package ru.bmstu.lab1.task_37;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 37. Разворот массива.
 * Выход: массив, записанный в обратном порядке.
 * Сложность по времени — O(n).
 * Память — O(n) для массива; дополнительно O(1).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int[] values = readArray(input);
        reverse(values, 0, values.length - 1);
        printArray(output, values);
    }

    /** Выводит массив одной строкой. */
    private static void printArray(PrintStream output, int[] values) {
        printPrefix(output, values, values.length);
    }

    /** Выводит первые size элементов, включая пустую строку при size = 0. */
    private static void printPrefix(PrintStream output, int[] values, int size) {
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                output.print(' ');
            }
            output.print(values[i]);
        }
        output.println();
    }

    /** Читает n и затем n элементов. */
    private static int[] readArray(Scanner input) {
        return readValues(input, input.nextInt());
    }

    /** Читает заданное число элементов. */
    private static int[] readValues(Scanner input, int size) {
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = input.nextInt();
        }
        return values;
    }

    /** Разворачивает включительный диапазон на месте. */
    private static void reverse(int[] values, int left, int right) {
        while (left < right) {
            swap(values, left++, right--);
        }
    }

    /** Меняет местами два элемента. */
    private static void swap(int[] values, int first, int second) {
        int temporary = values[first];
        values[first] = values[second];
        values[second] = temporary;
    }
}
