package ru.bmstu.lab01.task_59;

import java.util.Scanner;

/**
 * Класс для решения задачи 59: вычисление определителя матрицы 3x3.
 * <p>
 * Ключевая особенность: вычисление выполняется по готовой математической 
 * формуле (правило треугольника/Саррюса) за константное время O(1), 
 * так как размер матрицы фиксирован.
 * </p>
 * 
 * Условие задачи:
 * Вход: матрица (3 x 3) из целых чисел.
 * Выход: определитель матрицы.
 * Сложность: средняя.
 */
public class Task59 {

    /**
     * Вычисляет определитель матрицы 3x3.
     * 
     * @param matrix квадратная матрица размером 3x3
     * @return определитель матрицы
     */
    public int calculateDeterminant(int[][] matrix) {
        // Проверка на пустую матрицу или неверный размер
        if (matrix == null || matrix.length != 3 || matrix[0].length != 3) {
            return 0; // Или throw new IllegalArgumentException("Матрица должна быть 3x3");
        }

        return matrix[0][0] * matrix[1][1] * matrix[2][2]
             + matrix[0][1] * matrix[1][2] * matrix[2][0]
             + matrix[0][2] * matrix[1][0] * matrix[2][1]
             - matrix[0][2] * matrix[1][1] * matrix[2][0]
             - matrix[0][0] * matrix[1][2] * matrix[2][1]
             - matrix[0][1] * matrix[1][0] * matrix[2][2];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            
            int[][] matrix = new int[3][3];
            System.out.println("Введите элементы матрицы 3x3 (построчно):");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }
            
            Task59 solver = new Task59();
            int det = solver.calculateDeterminant(matrix);
            
            System.out.println("Определитель: " + det);
        }
    }
}