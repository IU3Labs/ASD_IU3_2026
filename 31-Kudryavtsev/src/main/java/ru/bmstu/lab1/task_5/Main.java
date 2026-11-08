package ru.bmstu.lab1.task_5;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 5. Найти сумму элементов массива с чётными индексами: 0, 2, 4 и далее.
 * Ввод: размер массива и его элементы. Вывод: сумма выбранных элементов.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив и выводит сумму элементов на чётных позициях. */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        output.println(sumEvenIndices(numbers));
    }

    /** Складывает элементы через один, начиная с нулевого индекса. */
    static long sumEvenIndices(int[] numbers) {
        // Сумма нескольких int может выходить за пределы int.
        long sum = 0;
        for (int index = 0; index < numbers.length; index += 2) {
            sum += numbers[index];
        }
        return sum;
    }
}
