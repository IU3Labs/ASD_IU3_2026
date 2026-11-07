package ru.bmstu.lab1.task_50;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 50. Сумма столбцов матрицы.
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из m элементов — сумм столбцов.
 *
 * Ввод:
 * n m
 * дальше n строк по m чисел
 * Вывод: суммы столбцов через пробел.
 */

/**
 * Считает сумму каждого столбца матрицы.
 */
public class ColumnSums {

    /**
     * Возвращает суммы столбцов. Индекс результата — номер столбца.
     */
    public static long[] sumColumns(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        long[] sums = new long[columns];
        for (int column = 0; column < columns; column++) {
            long sum = 0;
            for (int row = 0; row < rows; row++) {
                sum = sum + matrix[row][column];
            }
            sums[column] = sum;
        }
        return sums;
    }

    /**
     * Читает матрицу и печатает суммы столбцов.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int[][] matrix = ArrayIO.readMatrix(scanner, rows, columns);
        ArrayIO.printNumbers(sumColumns(matrix));
    }
}
