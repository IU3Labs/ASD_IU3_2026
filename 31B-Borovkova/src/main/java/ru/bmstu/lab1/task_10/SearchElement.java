/*Задача 10. Поиск элемента с условием
Вход: массив из (n) целых чисел и число (k).
Выход: индекс последнего элемента, кратного (k). Если такого нет — вывести -1.
Ограничения: k ≠ 0.
Сложность: базовая */

package ru.bmstu.lab1.task_10;

import java.util.Scanner;

/**
 * Решение задачи 10: поиск индекса последнего элемента, кратного k.
 */
public class SearchElement {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Ответ, если элементов, кратных k, в массиве нет. */
    private static final int NOT_FOUND = -1;

    /**
     * Читает n, n целых чисел и k, печатает индекс последнего элемента, кратного k.
     * Если таких элементов нет, печатает -1.
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
        if (k == 0) {
            System.out.println("Ошибка: k не должно быть равно нулю");
            return;
        }

        // Идем с начала и запоминаем каждое совпадение: в конце останется последнее
        int index = 0;
        boolean found = false;
        for (int i = 0; i < n; i++) {
            if (a[i] % k == 0) {
                index = i; 
                found = true;
            }
        }
        if (!found) {
            System.out.println(NOT_FOUND);
        } else {
            System.out.println(index);
        }

        scanner.close();
    }

}