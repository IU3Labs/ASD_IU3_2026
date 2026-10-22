/*Задача 48. Транспонирование матрицы
Вход: квадратная матрица (n x n).
Выход: транспонированная матрица.
Требование: транспонирование на месте, без создания нового массива.
Сложность: средняя */

package ru.bmstu.lab1.task_48;

import java.util.Scanner;

/**
 * Решение задачи 48: транспонирование квадратной матрицы на месте.
 */
public class Transpose {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы. */
    private static final int MAX_SIZE = 1000;

    /**
     * Читает n и матрицу n x n, транспонирует её на месте и печатает результат.
     * Новый массив не создаётся: элементы a[i][j] и a[j][i] меняются местами.
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

        // Читаем матрицу построчно
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

        // Меняем местами элементы, симметричные относительно главной диагонали.
        // j начинается с i + 1: идём только над диагональю, иначе каждая пара
        // поменялась бы дважды и матрица вернулась бы к исходной.
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int t = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = t;
            }
        }

        // Печатаем матрицу: числа в строке через пробел, каждая строка с новой строки
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j]);
                if (j < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}
