package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Предоставляет общий метод чтения целочисленной матрицы
 */
public final class MatrixInput {

    private MatrixInput() {
    }

    /**
     * Считывает положительные размеры и элементы прямоугольной матрицы
     *
     * @param input источник входных данных
     * @return считанная матрица
     */
    public static int[][] readMatrix(Scanner input) {
        int rows = input.nextInt();
        int columns = input.nextInt();
        int[][] matrix = new int[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                matrix[row][column] = input.nextInt();
            }
        }

        return matrix;
    }
}