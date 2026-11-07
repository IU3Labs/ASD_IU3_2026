package ru.bmstu.lab1.task_50;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 50. Сумма столбцов матрицы.
 * Выход: массив из m элементов — сумм столбцов матрицы.
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
        long[] sums = new long[columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                sums[column] += input.nextInt();
            }
        }
        for (int column = 0; column < columns; column++) {
            if (column > 0) {
                output.print(' ');
            }
            output.print(sums[column]);
        }
        output.println();
    }

}
