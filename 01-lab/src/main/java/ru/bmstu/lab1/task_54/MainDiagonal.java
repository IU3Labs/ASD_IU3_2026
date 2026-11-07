package ru.bmstu.lab1.task_54;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 54. Сумма элементов на главной диагонали.
 * Вход: квадратная матрица n x n.
 * Выход: сумма элементов на главной диагонали.
 *
 * Ввод:
 * n
 * дальше n строк по n чисел
 */

/**
 * Складывает элементы главной диагонали.
 */
public class MainDiagonal {

    /**
     * Возвращает сумму элементов, у которых номер строки равен номеру столбца.
     */
    public static long sumMainDiagonal(int[][] matrix) {
        long sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum = sum + matrix[i][i];
        }
        return sum;
    }

    /**
     * Читает квадратную матрицу и печатает сумму главной диагонали.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[][] matrix = ArrayIO.readMatrix(scanner, size, size);
        System.out.println(sumMainDiagonal(matrix));
    }
}
