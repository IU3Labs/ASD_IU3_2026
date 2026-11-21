/*Задача 49. Сумма строк матрицы
Вход: матрица (n x m) из целых чисел.
Выход: массив из (n) элементов — сумм строк матрицы.
Сложность: базовая */

package ru.bmstu.lab1.task_49;

import java.util.Scanner;

/**
 * Решение задачи 49: суммы строк матрицы без хранения самой матрицы.
 */
public class RowSums {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы. */
    private static final int MAX_SIZE = 1000;

    /**
     * Читает n, m и матрицу n x m, печатает суммы строк через пробел.
     * Матрица не хранится: элемент сразу прибавляется к сумме своей строки.
     * Суммы хранятся в long, так как сумма многих int может не поместиться в int.
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

        // Читаем матрицу и сразу прибавляем элемент к сумме строки i.
        // Массив long изначально заполнен нулями — это и есть начальные суммы.
        long[] rowSum = new long[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                rowSum[i] = rowSum[i] + scanner.nextInt();
            }
        }

        // Печатаем суммы через пробел
        for (int i = 0; i < n; i++) {
            System.out.print(rowSum[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}
