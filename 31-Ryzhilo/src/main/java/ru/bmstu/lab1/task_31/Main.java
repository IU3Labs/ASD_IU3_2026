package ru.bmstu.lab1.task_31;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Задача 31. Скалярное произведение.
 * Выход: скалярное произведение
 * Сложность по времени — O(n) арифметических операций.
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
        int[] first = readValues(input, n);
        BigInteger product = BigInteger.ZERO;
        for (int i = 0; i < n; i++) {
            long term = (long) first[i] * input.nextInt();
            product = product.add(BigInteger.valueOf(term));
        }
        output.println(product);
    }

    /** Читает заданное число элементов. */
    private static int[] readValues(Scanner input, int size) {
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = input.nextInt();
        }
        return values;
    }
}
