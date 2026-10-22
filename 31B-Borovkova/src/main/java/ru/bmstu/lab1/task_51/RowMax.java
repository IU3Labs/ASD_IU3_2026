/*Задача 51. Максимум в каждой строке
Вход: матрица (n x m) из целых чисел.
Выход: массив из (n) элементов — максимальных значений в каждой строке.
Сложность: базовая */

package ru.bmstu.lab1.task_51;

import java.util.Scanner;

/**
 * Решение задачи 51: максимум каждой строки матрицы без хранения самой матрицы.
 */
public class RowMax {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы. */
    private static final int MAX_SIZE = 1000;

    /**
     * Читает n, m и матрицу n x m, печатает максимумы строк через пробел.
     * Матрица не хранится: максимум строки обновляется сразу при чтении элемента.
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

        // Читаем матрицу и сразу обновляем максимум строки i
        int[] rowMax = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                int x = scanner.nextInt();
                // Первый элемент строки — начальный максимум этой строки
                if (j == 0 || x > rowMax[i]) {
                    rowMax[i] = x;
                }
            }
        }

        // Печатаем максимумы через пробел
        for (int i = 0; i < n; i++) {
            System.out.print(rowMax[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}