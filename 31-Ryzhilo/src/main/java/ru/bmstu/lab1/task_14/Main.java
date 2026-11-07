package ru.bmstu.lab1.task_14;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 14. Сортировка массива по невозрастанию методом вставок.
 * Сложность по времени — O(n²), в лучшем случае — O(n).
 * Память для хранения массива — O(n).
 * Дополнительная память сортировки — O(1).
 */
public class Main {

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив, сортирует его вставками и выводит результат. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        for (int i = 1; i < n; i++) {
            int value = values[i];
            int j = i - 1;

            // Сдвигаем меньшие элементы вправо, освобождая место для value.
            while (j >= 0 && values[j] < value) {
                values[j + 1] = values[j];
                j--;
            }

            // Вставляем сохранённое значение в отсортированную часть.
            values[j + 1] = value;
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
