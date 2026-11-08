package ru.bmstu.lab1.task_1;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 1. Найти минимальный и максимальный элементы массива целых чисел.
 * Ввод: размер от 2 до 99999 и элементы массива. Вывод: минимум и максимум через пробел.
 */
public class Main {

    /**
     * Запускает решение со стандартными потоками ввода и вывода.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        solve(input, System.out);
    }

    /**
     * Читает размер и элементы массива, выводит минимум и максимум.
     */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        int[] minMax = findMinMax(numbers);
        output.println(minMax[0] + " " + minMax[1]);
    }

    /**
     * Находит минимум и максимум за один проход, не изменяя исходный массив.
     * Принимает массив не менее чем из двух чисел. Возвращает минимум и максимум в этом порядке.
     */
    static int[] findMinMax(int[] numbers) {
        // Ноль может отсутствовать в массиве, поэтому начальные значения берём из самого массива.
        int minimum = numbers[0];
        int maximum = numbers[0];

        for (int index = 1; index < numbers.length; index++) {
            int current = numbers[index];
            if (current < minimum) {
                minimum = current;
            }
            if (current > maximum) {
                maximum = current;
            }
        }

        return new int[] {minimum, maximum};
    }
}
