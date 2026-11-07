package ru.bmstu.lab1.task_4;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 4. Все индексы элемента: вывести индексы всех элементов массива, равных x,
 * в порядке возрастания. Сложность по времени - O(n)
 */
public class Main {

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает n, массив из n чисел и число x, затем печатает индексы элементов, равных x,
     * через пробел в одной строке. Если таких элементов нет, печатается пустая строка.
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        int x = input.nextInt();

        //поиск вхождений x в массив: индексы накапливаем в StringBuilder,
        //так как конкатенация строк в цикле дала бы O(n^2)
        StringBuilder indices = new StringBuilder();

        for (int i = 0; i < n; i++) {
            if (numbers[i] == x) {
                if (!indices.isEmpty()) {
                    indices.append(' ');
                }
                indices.append(i);
            }
        }

        output.println(indices);
    }
}
