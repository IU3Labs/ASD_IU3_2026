package ru.bmstu.lab1.task_3;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 3. Подсчитать количество вхождений заданного числа в массив.
 * Ввод: размер массива, его элементы и искомое число. Вывод: количество совпадений.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и выводит количество вхождений. */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        int target = input.nextInt();
        int result = countOccurrences(numbers, target);
        output.println(result);
    }

    /** Считает все элементы, равные искомому числу, не изменяя массив. */
    static int countOccurrences(int[] numbers, int target) {
        int count = 0;
        for (int number : numbers) {
            if (number == target) {
                count++;
            }
        }
        return count;
    }
}
