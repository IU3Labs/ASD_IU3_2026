package ru.bmstu.lab1.task_7;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 7. Найти второй по величине элемент массива, в котором все числа различны.
 * Ввод: размер массива и его элементы. Вывод: второй максимум.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив и выводит второй максимум. */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        output.println(findSecondMaximum(numbers));
    }

    /** Хранит два наибольших числа среди уже просмотренных элементов. */
    static int findSecondMaximum(int[] numbers) {
        int maximum = Math.max(numbers[0], numbers[1]);
        int secondMaximum = Math.min(numbers[0], numbers[1]);
        for (int index = 2; index < numbers.length; index++) {
            int number = numbers[index];
            if (number > maximum) {
                // Прежний максимум становится вторым.
                secondMaximum = maximum;
                maximum = number;
            } else if (number > secondMaximum) {
                secondMaximum = number;
            }
        }
        return secondMaximum;
    }
}
