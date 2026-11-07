package ru.bmstu.lab1.task_52;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 52. Минимум в каждом столбце.
 * Выход: массив из m элементов — минимальных значений в каждом столбце.
 * Сложность по времени — O(nm).
 * Память — O(m).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int rows = input.nextInt();
        int columns = input.nextInt();
        int[] minimums = new int[columns];
        for (int column = 0; column < columns; column++) {
            minimums[column] = Integer.MAX_VALUE;
        }
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                minimums[column] = Math.min(minimums[column], input.nextInt());
            }
        }
        printArray(output, minimums);
    }

    /** Выводит элементы массива через пробел. */
    private static void printArray(PrintStream output, int[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                output.print(' ');
            }
            output.print(values[i]);
        }
        output.println();
    }
}
