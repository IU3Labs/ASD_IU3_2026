package ru.bmstu.lab1.task_25;

import java.util.Scanner;

/**
 * Задача 25. Вычисляет сумму элементов целочисленного массива.
 */
public class Main {

    /**
     * Читает количество элементов и сами элементы, выводит их сумму.
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int[] numbers = new int[n];

            for (int i = 0; i < n; i++) {
                numbers[i] = scanner.nextInt();
            }

            long sum = 0;

            for (int number : numbers) {
                sum += number;
            }

            System.out.println(sum);
        }
    }
}