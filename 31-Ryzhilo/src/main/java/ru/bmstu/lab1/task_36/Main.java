package ru.bmstu.lab1.task_36;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 36. Бегущая сумма.
 * Выход: массив той же длины, где i-й элемент равен сумме первых i + 1 элементов исходного массива.
 * Сложность по времени — O(n).
 * Память — O(1).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += input.nextInt();
            if (i > 0) {
                output.print(' ');
            }
            output.print(sum);
        }
        output.println();
    }

}
