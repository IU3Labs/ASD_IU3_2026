package ru.bmstu.lab1.task_15;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 15. Пузырьковая сортировка по неубыванию с подсчётом обменов.
 * Выход: отсортированный массив, затем количество обменов на отдельной строке.
 * Сложность по времени — O(n²), в лучшем случае — O(n).
 * Память для хранения массива — O(n).
 * Дополнительная память сортировки — O(1).
 */
public class Main {

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив, сортирует его и выводит результат с количеством обменов. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        // Число обменов может превышать диапазон int.
        long exchanges = 0;

        for (int end = n - 1; end > 0; end--) {
            boolean changed = false;

            for (int i = 0; i < end; i++) {
                if (values[i] > values[i + 1]) {
                    // Меняем соседние элементы, нарушающие порядок.
                    int temporary = values[i];
                    values[i] = values[i + 1];
                    values[i + 1] = temporary;

                    exchanges++;
                    changed = true;
                }
            }

            // Флаг не изменен, значит массив уже отсортирован
            if (!changed) {
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                output.print(' ');
            }
            output.print(values[i]);
        }
        output.println();

        output.println(exchanges);
    }
}
