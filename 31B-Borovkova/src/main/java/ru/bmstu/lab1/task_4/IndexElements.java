/*Задача 4. Все индексы элемента
Вход: массив из (n) целых чисел и число (x).
Выход: все индексы элементов, равных (x), в порядке возрастания.
Сложность: базовая */

package ru.bmstu.lab1.task_4;

import java.util.Scanner;

/**
 * Решение задачи 4: вывод всех индексов элементов равных x.
 */
public class IndexElements {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Ответ, если элементов, равных x, в массиве нет. */
    private static final int NOT_FOUND = -1;

    /**
     * Читает n, n целых чисел и x, печатает все индексы элементов массива, равных x.
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
        int x = scanner.nextInt();

        boolean found = false;
        for (int i = 0; i < n; i++) {
            if (a[i] == x) {
                System.out.println(i); 
                found = true;
            }
        }
        if (!found) {
            System.out.println(NOT_FOUND);
        }

        scanner.close();
    }
    
}