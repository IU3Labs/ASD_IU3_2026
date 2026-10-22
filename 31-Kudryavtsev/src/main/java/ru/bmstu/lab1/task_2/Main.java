package ru.bmstu.lab1.task_2;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 2. Найти индекс первого вхождения числа в массив, при отсутствии вывести -1.
 * Ввод: размер массива, его элементы и искомое число. Индексация начинается с нуля.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив и искомое число, выводит индекс первого совпадения. */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        int target = input.nextInt();
        int result = findFirstIndex(numbers, target);
        output.println(result);
    }

    /** Возвращает индекс первого совпадения или -1, если совпадений нет. */
    static int findFirstIndex(int[] numbers, int target) {
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] == target) {
                return index;
            }
        }
        return -1;
    }
}
