/*Задача 45. Разбиение по опорному элементу
Вход: массив из (n) целых чисел и опорное значение (p).
Выход: массив, переставленный так, что сначала идут все элементы (< p), затем (= p), затем (> p).
Относительный порядок внутри групп не важен.
Требование: (O(n)), (O(1)) доп. памяти.
Сложность: средняя */

package ru.bmstu.lab1.task_45;

import java.util.Scanner;

/**
 * Решение задачи 45: трёхчастное разбиение («голландский флаг») за один проход на месте.
 */
public class Partition {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и p, переставляет массив на месте так, что сначала идут
     * элементы меньше p, затем равные p, затем больше p, и печатает его через пробел.
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

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int p = scanner.nextInt();

            // Инвариант: a[0..lt-1] < p, a[lt..i-1] = p, a[i..gt] ещё не просмотрены, a[gt+1..n-1] > p
            int lt = 0;
            int i = 0;
            int gt = n - 1;
            while (i <= gt) {
                if (a[i] < p) {
                    swap(a, lt, i);
                    lt++;
                    i++;
                } else if (a[i] > p) {
                    // Пришедший с конца элемент ещё не проверен, поэтому i не сдвигаем
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
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

    /**
     * Меняет местами элементы a[i] и a[j].
     *
     * @param a массив
     * @param i индекс первого элемента
     * @param j индекс второго элемента
     */
    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
