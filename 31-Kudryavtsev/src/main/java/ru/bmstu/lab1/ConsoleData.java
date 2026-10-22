package ru.bmstu.lab1;

import java.io.PrintStream;
import java.util.Scanner;

/** Общие операции чтения массивов и матриц и вывода последовательностей чисел. */
public final class ConsoleData {

    private ConsoleData() {
    }

    /** Читает размер массива и указанное количество целых чисел. */
    public static int[] readArray(Scanner input) {
        int size = input.nextInt();
        int[] numbers = new int[size];
        for (int index = 0; index < size; index++) {
            numbers[index] = input.nextInt();
        }
        return numbers;
    }

    /** Читает матрицу заданного размера по строкам. */
    public static int[][] readMatrix(Scanner input, int rows, int columns) {
        int[][] matrix = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                matrix[row][column] = input.nextInt();
            }
        }
        return matrix;
    }

    /** Выводит целые числа через пробел; для пустого массива выводит пустую строку. */
    public static void printArray(int[] numbers, PrintStream output) {
        for (int index = 0; index < numbers.length; index++) {
            if (index > 0) {
                output.print(" ");
            }
            output.print(numbers[index]);
        }
        output.println();
    }

    /** Выводит суммы типа long через пробел. */
    public static void printArray(long[] numbers, PrintStream output) {
        for (int index = 0; index < numbers.length; index++) {
            if (index > 0) {
                output.print(" ");
            }
            output.print(numbers[index]);
        }
        output.println();
    }
}
