/*Задача 6. Элементы больше среднего
Вход: массив из (n) целых чисел.
Выход: количество элементов, строго больших среднего арифметического всех элементов.
Гарантируется, что среднее — целое число (сумма делится на (n)).
Сложность: базовая */


package ru.bmstu.lab1.task_6;

import java.util.Scanner;

/**
 * Решение задачи 6: вывод количества элементов массива строго больших среднего арифметического.
 */
public class NumElementsGreaterAverage {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Начальное значение суммы: пока не сложено ни одного элемента. */
    private static final int SUM_COUNT = 0;
    /** Начальное значение количества: пока не посчитано ни одного элемента. */
    private static final int COUNT = 0;
    
    /**
     * Читает n, n целых чисел, печатает количество элементов массива,
     * строго больших среднего арифметического всех элементов.
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

        long s = SUM_COUNT;
        int k = COUNT;

        for (int i = 0; i < n; i += 1) {
            s = s + a[i];
        }
        // Среднее значение: по условию сумма делится на n нацело
        long sr = s / n; 

        for (int i = 0; i < n; i++) {
            if (a[i] > sr) {
                k += 1;
            }
        }

        System.out.println(k);

        scanner.close();
    }
}