/*Задача 37. Разворот массива
Вход: массив из (n) целых чисел.
Выход: массив, записанный в обратном порядке.
Требование: выполнить разворот на месте, без создания нового массива.
Сложность: базовая */

package ru.bmstu.lab1.task_37;

import java.util.Scanner;

/**
 * Решение задачи 37: разворот массива на месте обменом симметричных элементов.
 */
public class ReverseArray {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, разворачивает массив на месте и печатает его через пробел.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
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

            // Меняем местами a[i] и a[j], сближая i и j с краёв к середине.
            // При нечётном n средний элемент остаётся на месте
            int i = 0;
            int j = n - 1;
            while (i < j) {
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
                i++;
                j--;
            }

            // Печатаем массив через пробел
            for (int k = 0; k < n; k++) {
                System.out.print(a[k]);
                if (k < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
