package ru.bmstu.lab1.task_35;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 35. Префиксные суммы.
 * Выход: для каждого запроса — сумму элементов с индексами от l до r включительно.
 * Сложность по времени — O(n + q), каждый запрос — O(1).
 * Память — O(n).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + input.nextInt();
        }
        int queries = input.nextInt();
        for (int i = 0; i < queries; i++) {
            int left = input.nextInt();
            int right = input.nextInt();
            output.println(prefix[right + 1] - prefix[left]);
        }
    }

}
