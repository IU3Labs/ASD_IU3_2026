package ru.bmstu.lab1.task_28;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 28. Медиана массива.
 * При нечётной длине медиана — центральный элемент отсортированного массива.
 * При чётной — среднее арифметическое двух центральных элементов.
 * Время — O(n log n). Общая память — O(n), дополнительная — O(1).
 */
public class Main {

    /** Запускает решение через стандартные потоки ввода и вывода. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        sort(numbers);

        int middle = n / 2;

        if (n % 2 != 0) {
            output.println(numbers[middle]);
        } else {
            // Приведение к long выполняется до сложения, чтобы избежать переполнения int.
            long sum = (long) numbers[middle - 1] + numbers[middle];
            output.println(sum / 2.0);
        }
    }

    /** Пирамидальная сортировка не требует дополнительного массива. */
    private static void sort(int[] numbers) {
        // Строим кучу: каждый родитель не меньше своих потомков.
        for (int root = numbers.length / 2 - 1; root >= 0; root--) {
            siftDown(numbers, root, numbers.length);
        }

        for (int end = numbers.length - 1; end > 0; end--) {
            // Максимум переносится в конец. Готовая часть больше не участвует в куче.
            swap(numbers, 0, end);
            siftDown(numbers, 0, end);
        }
    }

    /** Восстанавливает порядок в куче, опуская элемент к подходящему месту. */
    private static void siftDown(int[] numbers, int root, int size) {
        while (root * 2 + 1 < size) {
            int child = root * 2 + 1;

            // Выбираем большего потомка, чтобы родитель оказался не меньше обоих.
            if (child + 1 < size && numbers[child + 1] > numbers[child]) {
                child++;
            }

            if (numbers[root] >= numbers[child]) {
                break;
            }

            swap(numbers, root, child);
            root = child;
        }
    }

    private static void swap(int[] numbers, int first, int second) {
        int temporary = numbers[first];
        numbers[first] = numbers[second];
        numbers[second] = temporary;
    }
}
