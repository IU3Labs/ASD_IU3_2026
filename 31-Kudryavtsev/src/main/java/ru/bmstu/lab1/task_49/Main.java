package ru.bmstu.lab1.task_49;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 49. Найти сумму элементов каждой строки целочисленной матрицы.
 * Ввод: число строк, число столбцов и элементы по строкам. Вывод: суммы строк через пробел.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает матрицу и выводит сумму каждой строки. */
    static void solve(Scanner input, PrintStream output) {
        int rows = input.nextInt();
        int columns = input.nextInt();
        int[][] matrix = ConsoleData.readMatrix(input, rows, columns);
        long[] sums = sumRows(matrix);
        ConsoleData.printArray(sums, output);
    }

    /** Возвращает суммы строк; тип long позволяет складывать значения за пределами int. */
    static long[] sumRows(int[][] matrix) {
        long[] sums = new long[matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            for (int number : matrix[row]) {
                sums[row] += number;
            }
        }
        return sums;
    }
}
