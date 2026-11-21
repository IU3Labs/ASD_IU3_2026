/*Задача 24. Чётно-нечётная сортировка
Вход: массив из (n) целых чисел.
Выход: массив, в котором все чётные элементы отсортированы по возрастанию и идут первыми,
а нечётные отсортированы по убыванию и идут после.
Сложность: средняя */

package ru.bmstu.lab1.task_24;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Решение задачи 24: чётные по возрастанию, затем нечётные по убыванию.
 */
public class EvenOddSort {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, печатает через пробел сначала чётные элементы по возрастанию,
     * затем нечётные по убыванию.
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

        // Раскладываем элементы на чётные и нечётные
        int[] e = new int[k];
        int[] o = new int[n - k];
        int ei = 0;
        int oi = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                e[ei] = a[i];
                ei++;
            } else {
                o[oi] = a[i];
                oi++;
            }
        }
        Arrays.sort(e);
        Arrays.sort(o);

        // Собираем ответ: чётные по возрастанию, затем нечётные с конца — то есть по убыванию
        for (int i = 0; i < k; i++) {
            a[i] = e[i];
        }
        for (int i = 0; i < n - k; i++) {
            a[k + i] = o[n - k - 1 - i];
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
