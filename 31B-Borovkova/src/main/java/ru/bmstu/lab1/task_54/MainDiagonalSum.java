/*Задача 54. Сумма элементов на главной диагонали
Вход: квадратная матрица (n x n).
Выход: сумма элементов на главной диагонали.
Сложность: базовая */

package ru.bmstu.lab1.task_54;

import java.util.Scanner;

/**
 * Решение задачи 54: сумма элементов на главной диагонали квадратной матрицы без ее хранения.
 */
public class MainDiagonalSum {
    /** Наименьший допустимый размер матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер матрицы. */
    private static final int MAX_SIZE = 1000;
    /** Начальное значение суммы: пока не сложено ни одного элемента. */
    private static final int SUM_COUNT = 0;

    /**
     * Читает n и квадратную матрицу n x n, печатает сумму элементов на главной диагонали.
     * Матрица не хранится: элемент прибавляется к сумме сразу при чтении, если i == j.
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

        // Читаем матрицу и сразу складываем элементы, у которых номер строки равен номеру столбца
        long s = SUM_COUNT;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                int x = scanner.nextInt();
                if (i == j) {
                    s = s + x;
                }
            }
        }

        System.out.println(s);

        scanner.close();
    }
}