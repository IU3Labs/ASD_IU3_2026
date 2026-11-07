package ru.bmstu.lab1.task_17;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 17. Сортировка неотрицательных чисел по сумме цифр.
 * При равных суммах цифр сохраняется исходный порядок элементов.
 * Используется стабильная сортировка слиянием.
 * Сложность по времени — O(n log n) для чисел типа int.
 * Память для хранения массива — O(n).
 * Дополнительная память сортировки — O(n).
 */
public class Main {

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив, сортирует его по сумме цифр и выводит результат. */
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

    /** Объединяет две отсортированные половины, сохраняя порядок при равных суммах. */
    private static void merge(int[] values, int[] buffer, int left, int middle, int right) {
        int i = left;
        int j = middle;

        for (int position = left; position < right; position++) {
            // При равных суммах берём элемент слева: это обеспечивает стабильность.
            if (i < middle && (j == right || digitSum(values[i]) <= digitSum(values[j]))) {
                buffer[position] = values[i++];
            } else {
                buffer[position] = values[j++];
            }
        }

        for (int position = left; position < right; position++) {
            values[position] = buffer[position];
        }
    }

    /** Возвращает сумму цифр неотрицательного числа; для нуля возвращает 0. */
    private static int digitSum(int value) {
        int sum = 0;

        while (value > 0) {
            sum += value % 10;
            value /= 10;
        }

        return sum;
    }
}
