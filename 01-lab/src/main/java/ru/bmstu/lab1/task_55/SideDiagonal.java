package ru.bmstu.lab1.task_55;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 55. Сумма элементов на побочной диагонали.
 * Вход: квадратная матрица n x n.
 * Выход: сумма элементов на побочной диагонали
 * (от правого верхнего угла к левому нижнему).
 *
 * Ввод:
 * n
 * дальше n строк по n чисел
 */

/**
 * Складывает элементы побочной диагонали.
 */
public class SideDiagonal {

    /**
     * Возвращает сумму элементов от правого верхнего угла к левому нижнему.
     */
    public static long sumSideDiagonal(int[][] matrix) {
        long sum = 0;
        int lastColumn = matrix.length - 1;
        for (int row = 0; row < matrix.length; row++) {
            sum = sum + matrix[row][lastColumn - row];
        }
        return sum;
    }

    /**
     * Читает квадратную матрицу и печатает сумму побочной диагонали.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[][] matrix = ArrayIO.readMatrix(scanner, size, size);
        System.out.println(sumSideDiagonal(matrix));
    }
}
