package ru.bmstu.lab1.task_2;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 2. Найти индекс первого вхождения заданного числа.
 */
public class Main {

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает массив и искомое число, выводит индекс первого вхождения (индекс записывается в переменную index).
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        int x = input.nextInt();
        int index = -1;

        for (int i = 0; i < n; i++) {
            if (numbers[i] == x) {
                index = i;
                break;
            }
        }

        output.println(index);
    }
}
