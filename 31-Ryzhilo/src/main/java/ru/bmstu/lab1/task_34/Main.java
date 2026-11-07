package ru.bmstu.lab1.task_34;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 34. Максимальная сумма подмассива.
 * Выход: максимальная сумму непрерывного подмассива.
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
        long endingHere = input.nextInt();
        long best = endingHere;
        for (int i = 1; i < n; i++) {
            int value = input.nextInt();
            endingHere = Math.max(value, endingHere + value);
            best = Math.max(best, endingHere);
        }
        output.println(best);
    }

}
