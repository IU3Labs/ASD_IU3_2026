/*Задача 19. Объединение двух отсортированных массивов
Вход: два массива, каждый отсортирован по неубыванию; размеры (n) и (m).
Выход: один отсортированный массив — результат слияния.
Требование: O(n + m).
Сложность: средняя */

package ru.bmstu.lab1.task_19;

import java.util.Scanner;

/**
 * Решение задачи 19: слияние двух отсортированных массивов двумя указателями за O(n + m).
 */
public class MergeArrays {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, m, m целых чисел (оба массива неубывающие)
     * и печатает через пробел результат их слияния.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] a = readSortedArray(scanner);
        if (a == null) {
            return;
        }
        int[] b = readSortedArray(scanner);
        if (b == null) {
            return;
        }
        int n = a.length;
        int m = b.length;

        // Два указателя: на каждом шаге берём меньший из a[i] и b[j].
        // При равенстве берём из a — это сохраняет порядок (слияние устойчиво).
        int[] c = new int[n + m];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < n && j < m) {
            if (a[i] <= b[j]) {
                c[k] = a[i];
                i++;
            } else {
                c[k] = b[j];
                j++;
            }
            k=k+1;
        }
        // Один из массивов закончился — дописываем остаток другого
        while (i < n) {
            c[k] = a[i];
            i++;
            k++;
        }
        while (j < m) {
            c[k] = b[j];
            j++;
            k++;
        }

        // Печатаем результат через пробел
        for (int t = 0; t < n + m; t++) {
            System.out.print(c[t]);
            if (t < n + m - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }

    /**
     * Читает длину массива и сам массив, проверяя, что он отсортирован по неубыванию.
     * При ошибке печатает сообщение и возвращает null.
     *
     * @param scanner источник ввода
     * @return прочитанный массив или null, если ввод некорректен
     */
    private static int[] readSortedArray(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return null;
        }
        int n = scanner.nextInt();
        if (n < MIN_LENGTH || n > MAX_LENGTH) {
            System.out.println("Ошибка: некорректная длина массива");
            return null;
        }

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return null;
            }
            a[i] = scanner.nextInt();
            if (i > 0 && a[i] < a[i - 1]) {
                System.out.println("Ошибка: массив должен быть отсортирован по неубыванию");
                return null;
            }
        }
        return a;
    }
}
