package ru.bmstu.lab1.task_54;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.ConsoleData;

/**
 * Задача 54. Найти сумму элементов на главной диагонали квадратной матрицы.
 * Ввод: размер n и элементы матрицы по строкам. Вывод: сумма главной диагонали.
 */
public class Main {

    /** Запускает решение с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает квадратную матрицу и выводит сумму её главной диагонали. */
    static void solve(Scanner input, PrintStream output) {
        int size = input.nextInt();
        int[][] matrix = ConsoleData.readMatrix(input, size, size);
        long result = sumMainDiagonal(matrix);
        output.println(result);
    }

    /** Складывает элементы с одинаковыми индексами строки и столбца, используя long. */
    static long sumMainDiagonal(int[][] matrix) {
        long sum = 0;
        for (int index = 0; index < matrix.length; index++) {
            sum += matrix[index][index];
        }
        return sum;
    }
}
