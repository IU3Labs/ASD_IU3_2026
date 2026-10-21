package ru.bmstu.lab1;
import java.util.Scanner;

/** Чтение и вывод массивов и матриц для задач лабораторной работы №1. */
public final class ArrayTools {
    private ArrayTools() {
    }

    /** Читает n целых чисел и возвращает массив. */
    public static int[] readArray(Scanner scanner, int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    /** Выводит первые size элементов массива через пробел. */
    public static void printArray(int[] array, int size) {
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(array[i]);
        }
        System.out.println();
    }

    /** Читает квадратную матрицу из n строк по n чисел. */
    public static int[][] readMatrix(Scanner scanner, int n) {
        int[][] matrix = new int[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = readArray(scanner, n);
        }
        return matrix;
    }

    /** Выводит каждую строку матрицы на новой строке. */
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            printArray(matrix[i], matrix[i].length);
        }
    }
}