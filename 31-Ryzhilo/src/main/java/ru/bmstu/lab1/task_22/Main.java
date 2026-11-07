package ru.bmstu.lab1.task_22;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 22. Сортировка по убыванию частоты встречаемости.
 * При одинаковой частоте значения располагаются по возрастанию.
 * Используется пирамидальная сортировка.
 * Сложность по времени — O(n log n).
 * Общая память — O(n), включая массив частот.
 */
public class Main {

    /** Запускает программу через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив, подсчитывает частоты и выводит значения в нужном порядке. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] values = new int[n];
        int[] frequencies = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        // Пока все частоты равны нулю, сортировка упорядочивает сами значения.
        sort(values, frequencies, n);

        int groups = collectGroups(values, frequencies);
        sort(values, frequencies, groups);

        boolean first = true;

        for (int i = 0; i < groups; i++) {
            for (int j = 0; j < frequencies[i]; j++) {
                if (!first) {
                    output.print(' ');
                }
                output.print(values[i]);
                first = false;
            }
        }

        output.println();
    }

    /** Записывает различные значения и их частоты в начало массивов. */
    private static int collectGroups(int[] values, int[] frequencies) {
        int groups = 0;
        int i = 0;

        while (i < values.length) {
            int value = values[i];
            int end = i + 1;

            while (end < values.length && values[end] == value) {
                end++;
            }

            values[groups] = value;
            frequencies[groups] = end - i;
            groups++;

            i = end;
        }

        return groups;
    }

    /** Сортирует первые size групп по частоте, затем по значению. */
    private static void sort(int[] values, int[] frequencies, int size) {
        for (int root = size / 2 - 1; root >= 0; root--) {
            siftDown(values, frequencies, root, size);
        }

        for (int end = size - 1; end > 0; end--) {
            swap(values, frequencies, 0, end);
            siftDown(values, frequencies, 0, end);
        }
    }

    /** Поднимает к корню группу, которая должна находиться позже в результате. */
    private static void siftDown(int[] values, int[] frequencies, int root, int size) {
        while (root * 2 + 1 < size) {
            int child = root * 2 + 1;

            if (child + 1 < size && comesAfter(values, frequencies, child + 1, child)) {
                child++;
            }

            if (!comesAfter(values, frequencies, child, root)) {
                break;
            }

            swap(values, frequencies, root, child);
            root = child;
        }
    }

    /** Проверяет, должна ли первая группа идти после второй. */
    private static boolean comesAfter(int[] values, int[] frequencies, int first, int second) {
        if (frequencies[first] != frequencies[second]) {
            return frequencies[first] < frequencies[second];
        }

        return values[first] > values[second];
    }

    /** Меняет местами значения вместе с соответствующими частотами. */
    private static void swap(int[] values, int[] frequencies, int first, int second) {
        int temporary = values[first];
        values[first] = values[second];
        values[second] = temporary;

        temporary = frequencies[first];
        frequencies[first] = frequencies[second];
        frequencies[second] = temporary;
    }
}
