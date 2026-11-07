package ru.bmstu.lab1.task_25;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 25. Найти сумму всех элементов массива.
 * Сумма хранится в long для предотвращения переполнения int.
 * Сложность по времени — O(n).
 * Дополнительная память — O(1): элементы обрабатываются при чтении.
 */
public class Main {

    /** Запускает программу через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает размер и элементы массива, затем выводит их сумму. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += input.nextInt();
        }

        output.println(sum);
    }
}
