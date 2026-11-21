/*Задача 47. Поворот массива на 90 градусов
Вход: двумерный массив (n x m).
Выход: массив (m x n), полученный поворотом на 90 градусов по часовой стрелке.
Сложность: средняя */

package ru.bmstu.lab1.task_47;

import java.util.Scanner;

/**
 * Решение задачи 47: поворот матрицы n x m на 90 градусов по часовой стрелке.
 */
public class RotateMatrix {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы. */
    private static final int MAX_SIZE = 1000;

    /**
     * Читает n, m и матрицу n x m, печатает матрицу m x n, повёрнутую на 90 градусов
     * по часовой стрелке: числа в строке через пробел, каждая строка с новой строки.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int n = scanner.nextInt();
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int m = scanner.nextInt();
            if (n < MIN_SIZE || n > MAX_SIZE || m < MIN_SIZE || m > MAX_SIZE) {
                System.out.println("Ошибка: некорректный размер матрицы");
                return;
            }

            // Читаем матрицу построчно
            int[][] a = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (!scanner.hasNextInt()) {
                        System.out.println("Ошибка: должно быть целое число!");
                        return;
                    }
                    a[i][j] = scanner.nextInt();
                }
            }

            // Строка i результата — это столбец i исходной матрицы, прочитанный снизу вверх:
            // r[i][j] = a[n-1-j][i]. Новую матрицу не создаём, сразу печатаем
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(a[n - 1 - j][i]);
                    if (j < n - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }
}
