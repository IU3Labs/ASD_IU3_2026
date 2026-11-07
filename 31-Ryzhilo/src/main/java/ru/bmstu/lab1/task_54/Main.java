package ru.bmstu.lab1.task_54;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 54. Сумма элементов на главной диагонали.
 * Выход: сумма элементов на главной диагонали.
 * Сложность по времени — O(n²), включая чтение матрицы.
 * Память — O(1).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int size = input.nextInt();
        long sum = 0;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int value = input.nextInt();
                if (row == column) {
                    sum += value;
                }
            }
        }
        output.println(sum);
    }

}
