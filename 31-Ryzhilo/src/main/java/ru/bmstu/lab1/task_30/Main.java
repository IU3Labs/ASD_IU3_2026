package ru.bmstu.lab1.task_30;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 30. Дисперсия.
 * Выход: выборочную дисперсию по формуле.
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
        double mean = 0;
        double squaredDeviations = 0;
        // Алгоритм Уэлфорда избегает вычитания двух больших близких сумм квадратов.
        for (int count = 1; count <= n; count++) {
            double value = input.nextInt();
            double delta = value - mean;
            mean += delta / count;
            squaredDeviations += delta * (value - mean);
        }
        // В условии формула отсутствует: здесь принята исправленная выборочная дисперсия.
        output.println(squaredDeviations / (n - 1));
    }

}
