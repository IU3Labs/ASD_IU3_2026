/*Задача 53. Седловые точки
Вход: матрица (n x m) из целых чисел.
Выход: все седловые точки — элементы, одновременно минимальные в своей строке
и максимальные в своём столбце. Вывести в формате (индекс_строки, индекс_столбца, значение).
Сложность: средняя */

package ru.bmstu.lab1.task_53;

import java.util.Scanner;

/**
 * Решение задачи 53: поиск седловых точек через заранее посчитанные минимумы строк и максимумы столбцов.
 */
public class SaddlePoints {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы (чтобы матрица помещалась в память). */
    private static final int MAX_SIZE = 1000;
    /** Ответ, если седловых точек нет. */
    private static final int NOT_FOUND = -1;

    /**
     * Читает n, m и матрицу n x m, печатает все седловые точки в формате (строка, столбец, значение)
     * в порядке обхода по строкам. Если седловых точек нет, печатает -1.
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

        // Минимум каждой строки
        int[] rowMin = new int[n];
        for (int i = 0; i < n; i++) {
            rowMin[i] = a[i][0];
            for (int j = 1; j < m; j++) {
                if (a[i][j] < rowMin[i]) {
                    rowMin[i] = a[i][j];
                }
            }
        }

        // Максимум каждого столбца
        int[] colMax = new int[m];
        for (int j = 0; j < m; j++) {
            colMax[j] = a[0][j];
            for (int i = 1; i < n; i++) {
                if (a[i][j] > colMax[j]) {
                    colMax[j] = a[i][j];
                }
            }
        }

        // Седловая точка: равна минимуму своей строки и максимуму своего столбца
        boolean found = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] == rowMin[i] && a[i][j] == colMax[j]) {
                    System.out.println("(" + i + ", " + j + ", " + a[i][j] + ")");
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println(NOT_FOUND);
        }

        scanner.close();
    }
}