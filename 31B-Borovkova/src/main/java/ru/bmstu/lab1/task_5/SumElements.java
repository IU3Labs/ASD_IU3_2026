package ru.bmstu.lab1.task_5;

import java.util.Scanner;

/**
 * Решение задачи 5: вывод суммы элементов массива с четными индексами.
 */
public class SumElements {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Начальное значение суммы: пока не сложено ни одного элемента. */
    private static final int SUM_COUNT = 0;

    /**
     * Читает n, n целых чисел, печатает сумму элементов массива с четными индексами.
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

        for (int i = 0; i < n; i += 2) {
            s = s + a[i];
        }
        System.out.println(s);

        scanner.close();
    }
    
}