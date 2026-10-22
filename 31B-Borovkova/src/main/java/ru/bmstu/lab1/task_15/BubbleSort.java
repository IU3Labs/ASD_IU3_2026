/*Задача 15. Пузырьковая сортировка с подсчётом обменов
Вход: массив из (n) целых чисел.
Выход: отсортированный массив и количество обменов, выполненных в процессе пузырьковой сортировки.
Требование: реализовать пузырьковую сортировку с досрочным выходом, если массив уже отсортирован.
Сложность: базовая */

package ru.bmstu.lab1.task_15;

import java.util.Scanner;

/**
 * Решение задачи 15: пузырьковая сортировка с досрочным выходом и подсчётом обменов.
 */
public class BubbleSort {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, сортирует их пузырьком по неубыванию.
     * Печатает в первой строке отсортированный массив через пробел,
     * во второй — количество выполненных обменов.
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

        // Обменов может быть до n(n-1)/2 ≈ 5·10^9 — это больше int, поэтому long
        long swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            // После прохода i самый большой из оставшихся элементов «всплывает» на место n-1-i
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
                    swaps++;
                    swapped = true;
                }
            }
            // За весь проход не было ни одного обмена — массив уже отсортирован
            if (!swapped) {
                break;
            }
        }

        // Печатаем массив через пробел, затем количество обменов
        for (int i = 0; i < n; i++) {
            System.out.print(a[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.println(swaps);

        scanner.close();
    }
}
