/*Задача 9. Проверка отсортированности
Вход: массив из (n) целых чисел.
Выход: YES, если массив неубывающий; NO иначе.
Сложность: базовая */

package ru.bmstu.lab1.task_9;

import java.util.Scanner;

/**
 * Решение задачи 9: проверка, что массив неубывающий.
 */
public class SortedCheck {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, печатает YES, если каждый следующий элемент не меньше предыдущего,
     * и NO иначе.
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

        // Флаг: true, пока не найдена пара, где следующий элемент меньше предыдущего
        boolean f = true;

        for (int i = 0; i < n - 1; i++) {
            if (a[i + 1] < a[i]) {
                f = false;
                break;
            }
        }

        if (f) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        scanner.close();
    }
}
