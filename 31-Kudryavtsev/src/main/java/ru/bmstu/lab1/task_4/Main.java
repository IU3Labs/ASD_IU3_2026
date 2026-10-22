package ru.bmstu.lab1.task_4;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 4. Вывести все индексы заданного числа в массиве в порядке возрастания.
 * Ввод: размер массива, его элементы и искомое число. При отсутствии совпадений выводится пустая строка.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и выводит найденные индексы через пробел. */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        int target = input.nextInt();
        int[] indices = findAllIndices(numbers, target);
        ConsoleData.printArray(indices, output);
    }

    /** Возвращает индексы всех совпадений, сохраняя исходный массив. */
    static int[] findAllIndices(int[] numbers, int target) {
        // Совпасть могут все элементы, поэтому резервируем место под каждый индекс.
        int[] indices = new int[numbers.length];
        int count = 0;
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] == target) {
                indices[count] = index;
                count++;
            }
        }
        return Arrays.copyOf(indices, count);
    }
}
