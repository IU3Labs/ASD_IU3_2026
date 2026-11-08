package ru.bmstu.lab1.task_6;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 6. Подсчитать элементы массива, строго большие среднего арифметического.
 * Ввод: размер массива и его элементы. По условию среднее целое. Вывод: количество элементов.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив и выводит количество элементов больше среднего. */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        output.println(countAboveAverage(numbers));
    }

    /** Сначала вычисляет среднее, затем считает элементы строго больше него. */
    static int countAboveAverage(int[] numbers) {
        long sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        // По условию сумма делится на размер без остатка.
        long average = sum / numbers.length;
        int count = 0;
        for (int number : numbers) {
            if (number > average) {
                count++;
            }
        }
        return count;
    }
}
