package ru.bmstu.lab1.task_55;

import java.util.Scanner;

/**
 * Решение задачи 55: сумма элементов на побочной диагонали квадратной матрицы.
 */
public class AntiDiagonalSum {
    /** Наименьший допустимый размер матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер матрицы (чтобы матрица помещалась в память). */
    private static final int MAX_SIZE = 1000;
    /** Начальное значение суммы: пока не сложено ни одного элемента. */
    private static final int SUM_COUNT = 0;

    /**
     * Читает n и квадратную матрицу n x n, печатает сумму элементов на побочной диагонали.
     * Сумма хранится в long, так как сумма многих int может не поместиться в int.
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
        if (n < MIN_SIZE || n > MAX_SIZE) {
            System.out.println("Ошибка: некорректный размер матрицы");
            return;
        }

        // Читаем саму матрицу
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i][j] = scanner.nextInt();
            }
        }

        // В строке i побочная диагональ проходит через столбец n - 1 - i
        long s = SUM_COUNT;
        for (int i = 0; i < n; i++) {
            s = s + a[i][n - 1 - i];
        }

        System.out.println(s);

        scanner.close();
    }
}