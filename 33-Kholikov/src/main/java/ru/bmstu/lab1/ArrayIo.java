package ru.bmstu.lab1;

import java.io.PrintStream;
import java.util.Scanner;

/** Общий ввод и вывод массивов для задач лабораторной №1. */
public final class ArrayIo {
    private ArrayIo() {
    }

    /** Читает размер, затем элементы массива из стандартного формата задач. */
    public static int[] readArray(Scanner scanner) {
        int size = scanner.nextInt();
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = scanner.nextInt();
        }
        return numbers;
    }

    /** Выводит массив int в одну строку, разделяя элементы пробелами. */
    public static void printArray(int[] numbers, PrintStream output) {
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                output.print(" ");
            }
            output.print(numbers[i]);
        }
        output.println();
    }

    /** Выводит массив long в одну строку, разделяя элементы пробелами. */
    public static void printArray(long[] numbers, PrintStream output) {
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                output.print(" ");
            }
            output.print(numbers[i]);
        }
        output.println();
    }
}
