package ru.bmstu.lab1.task_6;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 6. Элементы больше среднего: количество элементов массива, строго больших среднего
 * арифметического всех элементов (гарантируется, что сумма делится на n). Сложность по времени - O(n)
 */
public class Main {

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает n и массив из n чисел, затем печатает количество элементов, строго больших среднего.
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        //сумма хранится в long: при n < 10^5 сумма значений типа int может выйти за пределы int
        long sum = 0;

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
            sum += numbers[i];
        }

        //среднее целое по условию, поэтому деление без потери точности
        long average = sum / n;
        int count = 0;

        //второй проход: среднее известно только после чтения всех данных
        for (int i = 0; i < n; i++) {
            if (numbers[i] > average) {
                count++;
            }
        }

        output.println(count);
    }
}
