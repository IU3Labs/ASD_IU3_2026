package ru.bmstu.lab1.task_51;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 51. Максимум в каждой строке.
 * Выход: массив из n элементов — максимальных значений в каждой строке.
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
            long result = Integer.MIN_VALUE;
            for (int column = 0; column < columns; column++) {
                result = Math.max(result, input.nextInt());
            }
            if (row > 0) {
                output.print(' ');
            }
            output.print(result);
        }
        output.println();
    }

}
