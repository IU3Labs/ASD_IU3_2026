package ru.bmstu.lab1.task_18;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 18. Сортировка только чётных элементов по неубыванию.
 * Нечётные элементы остаются на своих местах.
 * Чётность определяется значением элемента, а не его индексом.
 * Сложность по времени — O(n + k log k), где k — количество чётных элементов.
 * Память для хранения исходного массива — O(n).
 * Дополнительная память — O(k).
 */
public class Main {

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив, сортирует чётные элементы и выводит результат. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] values = new int[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
            if (values[i] % 2 == 0) {
                count++;
            }
        }

        int[] even = new int[count];
        int index = 0;

        for (int value : values) {
            if (value % 2 == 0) {
                even[index++] = value;
            }
        }

        int[] buffer = new int[count];
        sort(even, buffer, 0, count);

        // Возвращаем отсортированные числа только на места чётных элементов.
        index = 0;
        for (int i = 0; i < n; i++) {
            if (values[i] % 2 == 0) {
                values[i] = even[index++];
            }
        }

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

    /** Объединяет две отсортированные половины по неубыванию. */
    private static void merge(int[] values, int[] buffer, int left, int middle, int right) {
        int i = left;
        int j = middle;

        for (int position = left; position < right; position++) {
            if (i < middle && (j == right || values[i] <= values[j])) {
                buffer[position] = values[i++];
            } else {
                buffer[position] = values[j++];
            }
        }

        for (int position = left; position < right; position++) {
            values[position] = buffer[position];
        }
    }
}
