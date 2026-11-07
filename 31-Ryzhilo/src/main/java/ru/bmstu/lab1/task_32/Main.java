package ru.bmstu.lab1.task_32;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Задача 32. Сумма произведений пар.
 * Выход: сумма произведений всех пар элементов a_i * a_j для i < j.
 * Сложность по времени — O(n) арифметических операций.
 * Память — O(1) чисел BigInteger.
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        BigInteger prefix = BigInteger.ZERO;
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < n; i++) {
            BigInteger value = BigInteger.valueOf(input.nextInt());
            // Каждая новая пара заканчивается текущим элементом.
            sum = sum.add(prefix.multiply(value));
            prefix = prefix.add(value);
        }
        output.println(sum);
    }

}
