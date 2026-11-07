package ru.bmstu.lab1.task_27;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Задача 27. Найти сумму квадратов элементов массива.
 * BigInteger позволяет хранить сумму без переполнения.
 * При заданных ограничениях сложность по времени — O(n),
 * дополнительная память — O(1).
 * Элементы обрабатываются при чтении, массив не сохраняется.
 */
public class Main {

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает элементы и выводит точную сумму их квадратов. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < n; i++) {
            // Квадрат любого int помещается в long.
            long value = input.nextInt();
            long square = value * value;

            sum = sum.add(BigInteger.valueOf(square));
        }

        output.println(sum);
    }
}
