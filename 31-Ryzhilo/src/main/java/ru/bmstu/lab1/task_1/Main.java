package ru.bmstu.lab1.task_1;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 1. Найти минимальный и максимальный элементы массива.
 */
public class Main {

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает числа и выводит минимальное и максимальное значения.
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();

        int min = input.nextInt();
        int max = min;

        for (int i = 1; i < n; i++) {
            int value = input.nextInt();

            if (value < min) {
                min = value;
            }

            if (value > max) {
                max = value;
            }
        }

        output.println(min + " " + max);
    }
}
