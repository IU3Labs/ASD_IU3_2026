/*Задача 18. Сортировка только чётных
Вход: массив из (n) целых чисел.
Выход: тот же массив, в котором все чётные элементы отсортированы по неубыванию, а нечётные
остались на своих местах.
Сложность: средняя */

package ru.bmstu.lab1.task_18;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Решение задачи 18: сортировка чётных элементов на их позициях, нечётные не двигаются.
 */
public class EvenSort {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, печатает массив через пробел, в котором чётные элементы
     * отсортированы по неубыванию, а нечётные стоят на прежних местах.
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

        // Читаем массив и считаем количество чётных
        int[] a = new int[n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            a[i] = scanner.nextInt();
            if (a[i] % 2 == 0) {
                k++;
            }
        }

        // Выписываем чётные отдельно и сортируем их
        int[] e = new int[k];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                e[j] = a[i];
                j++;
            }
        }
        Arrays.sort(e);

        // Возвращаем отсортированные чётные на позиции чётных по порядку
        j = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                a[i] = e[j];
                j++;
            }
        }

        // Печатаем массив через пробел
        for (int i = 0; i < n; i++) {
            System.out.print(a[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}
