package ru.bmstu.lab1.task_9;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 9. Проверка отсортированности: YES, если массив неубывающий, иначе NO.
 * Сложность по времени - O(n)
 */
public class Main {

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает n и массив из n чисел, затем печатает YES, если массив неубывающий, и NO иначе.
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        boolean sorted = true;

        //достаточно сравнить соседние элементы; равные соседи допустимы (неубывание)
        for (int i = 1; i < n; i++) {
            if (numbers[i - 1] > numbers[i]) {
                sorted = false;
                break;
            }
        }

        output.println(sorted ? "YES" : "NO");
    }
}
