package ru.bmstu.lab1.task_49;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 49. Сумма строк матрицы.
 * Выход: массив из n элементов — сумм строк матрицы.
 * Сложность по времени — O(nm).
 * Память — O(1).
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
        for (int row = 0; row < rows; row++) {
            long result = 0;
            for (int column = 0; column < columns; column++) {
                result += input.nextInt();
            }
            if (row > 0) {
                output.print(' ');
            }
            output.print(result);
        }
        output.println();
    }

}
