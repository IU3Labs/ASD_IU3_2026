package ru.bmstu.lab01.task_47;

import java.util.Scanner;

/**
 * Класс для решения задачи 47: поворот двумерного массива на 90 градусов.
 * <p>
 * Ключевая особенность: так как размеры матрицы меняются с n x m на m x n, 
 * создается новый двумерный массив. Элемент matrix[i][j] из исходной матрицы 
 * переносится в позицию [j][n - 1 - i] новой матрицы.
 * </p>
 * 
 * Условие задачи:
 * Вход: двумерный массив (n x m).
 * Выход: массив (m x n), полученный поворотом на 90 градусов по часовой стрелке.
 * Сложность: средняя.
 */
public class Task47 {

/**
     * Поворачивает матрицу на 90 градусов по часовой стрелке.
     * 
     * @param matrix исходная матрица размером n x m
     * @return новая повернутая матрица размером m x n
     */
    public int[][] rotate90(int[][] matrix) {
        int n = matrix.length;
        if (n == 0) return new int[0][0];
        int m = matrix[0].length;
        
        // Создаем новую матрицу, где строки и столбцы поменялись местами
        int[][] result = new int[m][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // Элемент из [i][j] переходит в [j][n - 1 - i]
                result[j][n - 1 - i] = matrix[i][j];
            }
        }
        
        return result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Введите количество строк n и столбцов m:");
            if (!scanner.hasNextInt()) return;
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            
            int[][] matrix = new int[n][m];
            System.out.println("Введите элементы матрицы (построчно):");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }
            
            Task47 solver = new Task47();
            int[][] result = solver.rotate90(matrix);
            
            System.out.println("Результат (поворот на 90 градусов):");
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    System.out.print(result[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}