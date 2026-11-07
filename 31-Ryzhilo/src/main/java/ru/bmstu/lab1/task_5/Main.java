package ru.bmstu.lab1.task_5;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 5. Сумма элементов на чётных позициях: сумма элементов массива с чётными
 * индексами (0, 2, 4, ...). Сложность по времени - O(n). Сложность по памяти - O(n).
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

        //сумма хранится в long: при n < 10^5 сумма значений типа int может выйти за пределы int
        long sum = 0;

        //перебор только чётных индексов
        for (int i = 0; i < n; i += 2) {
            sum += numbers[i];
        }

        output.println(sum);
    }
}
