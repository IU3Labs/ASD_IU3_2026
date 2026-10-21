package ru.bmstu.lab1.task_48;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 48. Транспонировать квадратную матрицу на месте. */
public class Task48 {
    /** Читает размер и строки матрицы, затем выводит транспонированную матрицу. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] matrix = ArrayTools.readMatrix(scanner, n);
        transpose(matrix);
        ArrayTools.printMatrix(matrix);
    }

    /** Меняет местами элементы по разные стороны главной диагонали. */
    public static void transpose(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            // Каждую пару меняем один раз, диагональ не трогаем.
            for (int j = i + 1; j < matrix.length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
}