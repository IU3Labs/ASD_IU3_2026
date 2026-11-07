package ru.bmstu.lab1.task_26;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 26. Произведение элементов массива по модулю 10^9 + 7.
 * Результат находится в диапазоне от 0 до 10^9 + 6.
 * Сложность по времени — O(n).
 * Дополнительная память — O(1).
 */
public class Main {

    private static final long MODULUS = 1_000_000_007L;

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает элементы и выводит их произведение по модулю. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        long product = 1;

        for (int i = 0; i < n; i++) {
            long value = input.nextInt();

            // Приводим остаток к неотрицательному значению.
            value %= MODULUS;
            if (value < 0) {
                value += MODULUS;
            }

            // Берём остаток после каждого умножения.
            product = product * value % MODULUS;
        }

        output.println(product);
    }
}
