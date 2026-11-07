package ru.bmstu.lab1.task_49;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 49. Сумма строк матрицы.
 * Вход: матрица n x m из целых чисел.
 * Выход: массив из n элементов — сумм строк.
 *
 * Ввод:
 * n m
 * дальше n строк по m чисел
 * Вывод: суммы строк через пробел.
 */

/**
 * Считает сумму каждой строки матрицы.
 */
public class RowSums {

    /**
     * Возвращает суммы строк. Индекс результата — номер строки.
     */
    public static long[] sumRows(int[][] matrix) {
        long[] sums = new long[matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            long sum = 0;
            for (int column = 0; column < matrix[row].length; column++) {
                sum = sum + matrix[row][column];
            }
            sums[row] = sum;
        }
        return sums;
    }

    /**
     * Читает матрицу и печатает суммы строк.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int[][] matrix = ArrayIO.readMatrix(scanner, rows, columns);
        ArrayIO.printNumbers(sumRows(matrix));
    }
}
