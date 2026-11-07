package ru.bmstu.lab1.task_16;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 16. Сортировка массива по неубыванию модуля элементов.
 * При равных модулях отрицательное число идёт раньше положительного.
 * Используется сортировка слиянием.
 * Сложность по времени — O(n log n).
 * Память для хранения массива — O(n).
 * Дополнительная память сортировки — O(n).
 */
public class Main {

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив, сортирует его по модулю и выводит результат. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        int[] buffer = new int[n];
        sort(values, buffer, 0, n);

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                output.print(' ');
            }
            output.print(values[i]);
        }
        output.println();
    }

    /** Сортирует участок от left включительно до right исключительно. */
    private static void sort(int[] values, int[] buffer, int left, int right) {
        if (right - left < 2) {
            return;
        }

        int middle = left + (right - left) / 2;

        sort(values, buffer, left, middle);
        sort(values, buffer, middle, right);
        merge(values, buffer, left, middle, right);
    }

    /** Объединяет две отсортированные половины через временный массив. */
    private static void merge(int[] values, int[] buffer, int left, int middle, int right) {
        int i = left;
        int j = middle;

        for (int position = left; position < right; position++) {
            if (i < middle && (j == right || compare(values[i], values[j]) <= 0)) {
                buffer[position] = values[i++];
            } else {
                buffer[position] = values[j++];
            }
        }

        for (int position = left; position < right; position++) {
            values[position] = buffer[position];
        }
    }

    /** Сравнивает модули, а при их равенстве — сами значения. */
    private static int compare(int first, int second) {
        // Приведение к long позволяет корректно найти модуль Integer.MIN_VALUE.
        long firstAbsolute = Math.abs((long) first);
        long secondAbsolute = Math.abs((long) second);

        if (firstAbsolute < secondAbsolute) {
            return -1;
        }
        if (firstAbsolute > secondAbsolute) {
            return 1;
        }

        return Integer.compare(first, second);
    }
}
