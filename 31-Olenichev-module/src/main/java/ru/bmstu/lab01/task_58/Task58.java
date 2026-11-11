package ru.bmstu.lab01.task_58;

import java.util.Scanner;

/**
 * Класс для решения задачи 58: заполнение матрицы змейкой.
 * <p>
 * Ключевая особенность: матрица заполняется построчно. 
 * В четных строках счетчик идет слева направо, в нечетных — справа налево.
 * </p>
 * 
 * Условие задачи:
 * Вход: числа (n) и (m).
 * Выход: матрица (n x m), заполненная числами от 1 до (n x m) змейкой по строкам.
 * Сложность: средняя.
 */
public class Task58 {

 /**
     * Создает и заполняет матрицу змейкой.
     * 
     * @param n количество строк
     * @param m количество столбцов
     * @return заполненная матрица
     */
    public int[][] fillSnake(int n, int m) {
        int[][] matrix = new int[n][m];
        int counter = 1;
        
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                // Четная строка (0, 2, 4...): заполняем слева направо
                for (int j = 0; j < m; j++) {
                    matrix[i][j] = counter++;
                }
            } else {
                // Нечетная строка (1, 3, 5...): заполняем справа налево
                for (int j = m - 1; j >= 0; j--) {
                    matrix[i][j] = counter++;
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Введите n и m:");
            if (!scanner.hasNextInt()) return;
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            
            Task58 solver = new Task58();
            int[][] result = solver.fillSnake(n, m);
            
            System.out.println("Результат:");
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    System.out.print(result[i][j] + "\t");
                }
                System.out.println();
            }
        }
    }
}