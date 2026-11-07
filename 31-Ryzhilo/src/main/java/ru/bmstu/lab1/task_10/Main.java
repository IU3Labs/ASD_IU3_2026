package ru.bmstu.lab1.task_10;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 10. Поиск элемента с условием: индекс последнего элемента массива, кратного k.
 * Если такого нет - вывести -1 (k не равно 0). Сложность по времени - O(n)
 */
public class Main {

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает n, массив из n чисел и число k, затем печатает индекс последнего элемента,
     * кратного k, или -1, если такого элемента нет.
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        int k = input.nextInt();
        int result = -1;

        //идём с конца: первый найденный элемент и есть последний по индексу, дальше смотреть не нужно.
        //Остаток от деления равен 0 и для отрицательных чисел, и для нуля, и при отрицательном k
        for (int i = n - 1; i >= 0; i--) {
            if (numbers[i] % k == 0) {
                result = i;
                break;
            }
        }

        output.println(result);
    }
}
