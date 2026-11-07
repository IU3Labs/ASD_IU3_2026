package ru.bmstu.lab1.task_33;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 33. Количество инверсий.
 * Выход: количество пар (i, j) таких, что i < j и a_i > a_j.
 * Сложность по времени — O(n log n).
 * Память — O(n).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int[] values = readArray(input);
        output.println(countInversions(values, new int[values.length], 0, values.length));
    }
    private static long countInversions(int[] values, int[] buffer, int left, int right) {
        if (right - left < 2) {
            return 0;
        }
        int middle = left + (right - left) / 2;
        long count = countInversions(values, buffer, left, middle)
                + countInversions(values, buffer, middle, right);
        int i = left;
        int j = middle;
        for (int p = left; p < right; p++) {
            if (i < middle && (j == right || values[i] <= values[j])) {
                buffer[p] = values[i++];
            } else {
                buffer[p] = values[j++];
                // Правый элемент меньше всех оставшихся в левой половине.
                count += middle - i;
            }
        }
        System.arraycopy(buffer, left, values, left, right - left);
        return count;
    }

    /** Читает n и затем n элементов. */
    private static int[] readArray(Scanner input) {
        return readValues(input, input.nextInt());
    }

    /** Читает заданное число элементов. */
    private static int[] readValues(Scanner input, int size) {
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = input.nextInt();
        }
        return values;
    }
}
