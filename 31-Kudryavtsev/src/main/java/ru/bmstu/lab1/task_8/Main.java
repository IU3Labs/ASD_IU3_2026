package ru.bmstu.lab1.task_8;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 8. Разделить положительные и отрицательные элементы массива, сохранив их порядок и исключив нули.
 * Ввод: размер массива и его элементы. Вывод: положительные числа, затем отрицательные, на отдельных строках.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Выводит каждую группу через пробел; для пустой группы выводит пустую строку. */
    static void solve(Scanner input, PrintStream output) {
        int[] numbers = ConsoleData.readArray(input);
        int[][] groups = filterBySign(numbers);
        ConsoleData.printArray(groups[0], output);
        ConsoleData.printArray(groups[1], output);
    }

    /** Возвращает массив положительных чисел и массив отрицательных чисел. */
    static int[][] filterBySign(int[] numbers) {
        int positiveCount = 0;
        int negativeCount = 0;
        for (int number : numbers) {
            if (number > 0) {
                positiveCount++;
            } else if (number < 0) {
                negativeCount++;
            }
        }
        // После подсчёта можно выделить массивы ровно нужной длины.
        int[] positive = new int[positiveCount];
        int[] negative = new int[negativeCount];
        int positiveIndex = 0;
        int negativeIndex = 0;
        for (int number : numbers) {
            if (number > 0) {
                positive[positiveIndex++] = number;
            } else if (number < 0) {
                negative[negativeIndex++] = number;
            }
        }
        return new int[][] {positive, negative};
    }
}
