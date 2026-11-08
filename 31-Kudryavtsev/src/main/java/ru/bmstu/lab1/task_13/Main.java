package ru.bmstu.lab1.task_13;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 13. Отсортировать массив по неубыванию сортировкой выбором.
 * Ввод: размер массива и его элементы. Вывод: отсортированные элементы через пробел.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив, сортирует его и выводит результат. */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        selectionSort(numbers);
        ConsoleData.printArray(numbers, output);
    }

    /** Сортирует исходный массив выбором без встроенных функций сортировки. */
    static void selectionSort(int[] numbers) {
        for (int position = 0; position < numbers.length - 1; position++) {
            int smallestIndex = position;
            for (int index = position + 1; index < numbers.length; index++) {
                if (numbers[index] < numbers[smallestIndex]) {
                    smallestIndex = index;
                }
            }

            // Минимум оставшейся части занимает следующую позицию отсортированной части.
            if (smallestIndex != position) {
                int temporary = numbers[position];
                numbers[position] = numbers[smallestIndex];
                numbers[smallestIndex] = temporary;
            }
        }
    }
}
