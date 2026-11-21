/*Задача 21. K-й наименьший элемент
Вход: массив из (n) целых чисел и число (k) (1 <= k <= n).
Выход: (k)-й наименьший элемент массива.
Требование: отсортировать массив и взять элемент с индексом (k - 1).
Сложность: базовая */

package ru.bmstu.lab1.task_21;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Решение задачи 21: k-й наименьший элемент как элемент с индексом k - 1 после сортировки.
 */
public class KthSmallest {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и k, печатает k-й наименьший элемент массива.
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
        if (n < MIN_LENGTH || n > MAX_LENGTH) {
            System.out.println("Ошибка: некорректная длина массива");
            return;
        }

        // Читаем сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            a[i] = scanner.nextInt();
        }

        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int k = scanner.nextInt();
        if (k < 1 || k > n) {
            System.out.println("Ошибка: k должно быть от 1 до n");
            return;
        }

        // После сортировки по неубыванию k-й наименьший стоит на индексе k - 1
        Arrays.sort(a);
        System.out.println(a[k - 1]);

        scanner.close();
    }
}
