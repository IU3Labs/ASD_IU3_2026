package ru.bmstu.lab1.task_13;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 13. Сортировка массива по неубыванию методом выбора.
 * Сложность по времени — O(n²).
 * Память для хранения массива — O(n).
 * Дополнительная память сортировки — O(1).
 */
public class Main {

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив, сортирует его выбором и выводит результат. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        for (int i = 0; i < n - 1; i++) {
            int minimum = i; // Индекс предполагаемого минимума.

            for (int j = i + 1; j < n; j++) {
                if (values[j] < values[minimum]) {
                    minimum = j;
                }
            }

            // Меняем минимум с первым элементом неотсортированной части.
            int temporary = values[i];
            values[i] = values[minimum];
            values[minimum] = temporary;
        }

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                output.print(' ');
            }
            output.print(values[i]);
        }
        output.println();
    }
}
