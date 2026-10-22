/*Задача 52. Минимум в каждом столбце
Вход: матрица (n x m) из целых чисел.
Выход: массив из (m) элементов — минимальных значений в каждом столбце.
Сложность: базовая */

package ru.bmstu.lab1.task_52;

import java.util.Scanner;

/**
 * Решение задачи 52: минимум каждого столбца матрицы без хранения самой матрицы.
 */
public class ColumnMin {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы. */
    private static final int MAX_SIZE = 1000;

    /**
     * Читает n, m и матрицу n x m, печатает минимумы столбцов через пробел.
     * Матрица не хранится: минимум столбца обновляется сразу при чтении элемента.
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

        // Читаем матрицу и сразу обновляем минимум столбца j
        int[] colMin = new int[m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                int x = scanner.nextInt();
                // Элемент первой строки — начальный минимум своего столбца
                if (i == 0 || x < colMin[j]) {
                    colMin[j] = x;
                }
            }
        }

        // Печатаем минимумы через пробел
        for (int j = 0; j < m; j++) {
            System.out.print(colMin[j]);
            if (j < m - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}