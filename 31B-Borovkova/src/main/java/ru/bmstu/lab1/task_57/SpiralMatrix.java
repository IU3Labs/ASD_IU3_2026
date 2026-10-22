/*Задача 57. Спиральный обход матрицы
Вход: матрица (n x m) из целых чисел.
Выход: элементы матрицы, обойдённые по спирали от левого верхнего угла по часовой стрелке.
Сложность: средняя */

package ru.bmstu.lab1.task_57;

import java.util.Scanner;

/**
 * Решение задачи 57: обход матрицы по спирали по часовой стрелке от левого верхнего угла.
 */
public class SpiralMatrix {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы (чтобы матрица помещалась в память). */
    private static final int MAX_SIZE = 1000;

    /**
     * Читает n, m и матрицу n x m, печатает ее элементы в порядке спирального обхода через пробел.
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

        // Читаем саму матрицу
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

        // Границы еще не обойденной части матрицы
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = m - 1;

        // Элементы в порядке обхода и сколько их уже записано
        int[] b = new int[n * m];
        int k = 0;

        while (top <= bottom && left <= right) {
            // Верхняя строка слева направо
            for (int j = left; j <= right; j++) {
                b[k] = a[top][j];
                k += 1;
            }
            top += 1;

            // Правый столбец сверху вниз
            for (int i = top; i <= bottom; i++) {
                b[k] = a[i][right];
                k += 1;
            }
            right -= 1;

            // Нижняя строка справа налево, если строки еще остались
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    b[k] = a[bottom][j];
                    k += 1;
                }
                bottom -= 1;
            }

            // Левый столбец снизу вверх, если столбцы еще остались
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    b[k] = a[i][left];
                    k += 1;
                }
                left += 1;
            }
        }

        // Печатаем результат через пробел
        for (int i = 0; i < k; i++) {
            System.out.print(b[i]);
            if (i < k - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}