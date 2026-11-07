package ru.bmstu.lab1.task_29;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 29. Найти наиболее часто встречающийся элемент массива.
 * При одинаковой частоте выбирается наименьшее значение.
 * Используется пирамидальная сортировка, затем подсчёт повторений.
 * Сложность по времени — O(n log n).
 * Память для массива — O(n), дополнительная память сортировки — O(1).
 */
public class Main {

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив и выводит его моду. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        sort(values);

        int mode = values[0];
        int bestCount = 1;
        int count = 1;

        for (int i = 1; i < n; i++) {
            if (values[i] == values[i - 1]) {
                count++;
            } else {
                count = 1;
            }

            // При равной частоте сохраняем ранее найденное, меньшее значение.
            if (count > bestCount) {
                bestCount = count;
                mode = values[i];
            }
        }

        output.println(mode);
    }

    /** Сортирует массив по неубыванию пирамидальным методом. */
    private static void sort(int[] values) {
        for (int root = values.length / 2 - 1; root >= 0; root--) {
            siftDown(values, root, values.length);
        }

        for (int end = values.length - 1; end > 0; end--) {
            int temporary = values[0];
            values[0] = values[end];
            values[end] = temporary;

            siftDown(values, 0, end);
        }
    }

    /** Восстанавливает кучу: родитель должен быть не меньше своих детей. */
    private static void siftDown(int[] values, int root, int size) {
        while (root * 2 + 1 < size) {
            int child = root * 2 + 1;

            if (child + 1 < size && values[child + 1] > values[child]) {
                child++;
            }

            if (values[root] >= values[child]) {
                break;
            }

            int temporary = values[root];
            values[root] = values[child];
            values[child] = temporary;

            root = child;
        }
    }
}
