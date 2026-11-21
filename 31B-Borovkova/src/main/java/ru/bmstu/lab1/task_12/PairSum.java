/*Задача 12. Пара с заданной суммой
Вход: массив из (n) целых чисел и число (s).
Выход: индексы (i < j), для которых (a_i + a_j = s). Если пар несколько — вывести пару
с наименьшим (i), а при равенстве — с наименьшим (j). Если пары нет — вывести -1 -1.
Сложность: средняя */

package ru.bmstu.lab1.task_12;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Решение задачи 12: поиск пары с заданной суммой за один проход справа налево с HashMap.
 */
public class PairSum {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Индекс в ответе, если пара не найдена. */
    private static final int NOT_FOUND = -1;

    /**
     * Читает n, n целых чисел и s, печатает индексы i < j с a[i] + a[j] = s:
     * пару с наименьшим i, а при равных i — с наименьшим j. Если пары нет, печатает -1 -1.
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

        if (!scanner.hasNextLong()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        long s = scanner.nextLong();

        // nearest: число -> наименьший индекс справа от текущего i, где оно стоит.
        // Идем справа налево, поэтому каждая новая найденная пара имеет меньший i.
        HashMap<Long, Integer> nearest = new HashMap<>();
        int bestI = NOT_FOUND;
        int bestJ = NOT_FOUND;
        for (int i = n - 1; i >= 0; i--) {
            Integer j = nearest.get(s - a[i]);
            if (j != null) {
                bestI = i;
                bestJ = j;
            }
            nearest.put((long) a[i], i);
        }

        System.out.println(bestI + " " + bestJ);

        scanner.close();
    }
}
