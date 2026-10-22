/*Задача 22. Сортировка по частоте
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по убыванию частоты встречаемости элементов. При равных частотах —
по возрастанию значения.
Сложность: средняя */

package ru.bmstu.lab1.task_22;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Решение задачи 22: группировка равных значений после сортировки и устойчивая
 * сортировка подсчётом групп по убыванию частоты.
 */
public class FrequencySort {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, печатает их через пробел по убыванию частоты;
     * значения с равной частотой идут по возрастанию.
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

        // После сортировки равные значения стоят подряд: собираем группы
        // (значение v[g], частота f[g]); группы получаются по возрастанию значения
        Arrays.sort(a);
        int[] v = new int[n];
        int[] f = new int[n];
        int g = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || a[i] != a[i - 1]) {
                v[g] = a[i];
                g++;
            }
            f[g - 1]++;
        }

        // Сортировка групп подсчётом по частоте (частота от 1 до n).
        // count[c] — число групп с частотой c
        int[] count = new int[n + 1];
        for (int i = 0; i < g; i++) {
            count[f[i]]++;
        }
        // start[c] — позиция первой группы с частотой c; большие частоты идут раньше
        int[] start = new int[n + 1];
        for (int c = n - 1; c >= 1; c--) {
            start[c] = start[c + 1] + count[c + 1];
        }
        // Раскладываем группы по порядку — внутри одной частоты сохраняется возрастание значений
        int[] order = new int[g];
        for (int i = 0; i < g; i++) {
            order[start[f[i]]] = i;
            start[f[i]]++;
        }

        // Печатаем каждое значение столько раз, какова его частота
        int k = 0;
        for (int i = 0; i < g; i++) {
            int t = order[i];
            for (int c = 0; c < f[t]; c++) {
                System.out.print(v[t]);
                k++;
                if (k < n) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();

        scanner.close();
    }
}
