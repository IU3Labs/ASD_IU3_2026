/*Задача 58. Заполнение матрицы змейкой
Вход: числа (n) и (m).
Выход: матрица (n x m), заполненная числами от 1 до (n x m) змейкой по строкам:
первая строка слева направо, вторая — справа налево и т. д.
Сложность: средняя */

package ru.bmstu.lab1.task_58;

import java.util.Scanner;

/**
 * Решение задачи 58: заполнение матрицы числами от 1 до n * m змейкой по строкам.
 */
public class SnakeMatrix {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы (чтобы матрица помещалась в память). */
    private static final int MAX_SIZE = 1000;
    /** Число, с которого начинается заполнение. */
    private static final int FIRST_NUMBER = 1;

    /**
     * Читает n и m, печатает матрицу n x m, заполненную змейкой: числа в строке через пробел.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

        // Заполняем матрицу змейкой
        int[][] a = new int[n][m];
        int k = FIRST_NUMBER;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                // Четная строка (0, 2, ...) заполняется слева направо
                for (int j = 0; j < m; j++) {
                    a[i][j] = k;
                    k += 1;
                }
            } else {
                // Нечетная строка (1, 3, ...) заполняется справа налево
                for (int j = m - 1; j >= 0; j--) {
                    a[i][j] = k;
                    k += 1;
                }
            }
        }

        // Печатаем матрицу: числа в строке через пробел, каждая строка с новой строки
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(a[i][j]);
                if (j < m - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}