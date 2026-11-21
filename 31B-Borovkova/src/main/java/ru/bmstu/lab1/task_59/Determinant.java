/*Задача 59. Определитель матрицы 3×3
Вход: матрица (3 x 3) из целых чисел.
Выход: определитель матрицы.
Сложность: средняя */

package ru.bmstu.lab1.task_59;

import java.util.Scanner;

/**
 * Решение задачи 59: вычисление определителя матрицы 3 x 3 разложением по первой строке.
 */
public class Determinant {
    /** Размер матрицы по условию. */
    private static final int SIZE = 3;
    /** Наибольшее допустимое по модулю значение элемента, чтобы определитель поместился в long. */
    private static final int MAX_ABS_VALUE = 1_000_000;

    /**
     * Читает 9 целых чисел матрицы 3 x 3 построчно, печатает ее определитель.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Читаем саму матрицу
        long[][] a = new long[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i][j] = scanner.nextInt();
                if (a[i][j] < -MAX_ABS_VALUE || a[i][j] > MAX_ABS_VALUE) {
                    System.out.println("Ошибка: элемент слишком большой по модулю");
                    return;
                }
            }
        }

        // Разложение по первой строке: каждый элемент умножается на определитель 2 x 2
        // из оставшихся строк и столбцов, знаки чередуются: +, -, +
        long d = a[0][0] * (a[1][1] * a[2][2] - a[1][2] * a[2][1])
                - a[0][1] * (a[1][0] * a[2][2] - a[1][2] * a[2][0])
                + a[0][2] * (a[1][0] * a[2][1] - a[1][1] * a[2][0]);

        System.out.println(d);

        scanner.close();
    }
}
