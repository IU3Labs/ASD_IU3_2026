package ru.bmstu.lab1;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Общие методы ввода и вывода для задач первой лабораторной: чтение массивов и матриц, печать чисел через пробел.
 */
public final class ArrayIO {

    private ArrayIO() {
    }

    /**
     * Читает length целых чисел и возвращает их массивом.
     */
    public static int[] readArray(Scanner scanner, int length) {
        int[] numbers = new int[length];
        for (int i = 0; i < length; i++) {
            numbers[i] = scanner.nextInt();
        }
        return numbers;
    }

    /**
     * Читает матрицу из rows строк по columns чисел.
     */
    public static int[][] readMatrix(Scanner scanner, int rows, int columns) {
        int[][] matrix = new int[rows][];
        for (int row = 0; row < rows; row++) {
            matrix[row] = readArray(scanner, columns);
        }
        return matrix;
    }

    /**
     * Печатает числа через пробел в одну строку. Для пустого массива печатается пустая строка.
     */
    public static void printNumbers(int[] numbers) {
        System.out.println(Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));
    }

    /**
     * Печатает числа через пробел в одну строку. Для пустого массива печатается пустая строка.
     */
    public static void printNumbers(long[] numbers) {
        System.out.println(Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));
    }
}
