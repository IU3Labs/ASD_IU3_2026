/*Задача 29. Мода массива
Вход: массив из (n) целых чисел.
Выход: наиболее часто встречающийся элемент. Если несколько — вывести наименьший по значению.
Ограничения: (1 <= n <= 10^4).
Сложность: средняя */

package ru.bmstu.lab1.task_29;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Решение задачи 29: мода массива через сортировку и подсчёт длин серий равных элементов.
 */
public class Mode {
    /** Наименьшая допустимая длина массива (по условию задачи n >= 1). */
    private static final int MIN_LENGTH = 1;
    /** Наибольшая допустимая длина массива (по условию задачи n <= 10^4). */
    private static final int MAX_LENGTH = 10_000;

    /**
     * Читает n, n целых чисел и печатает самый частый элемент;
     * при нескольких таких — наименьший из них.
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

            // После сортировки равные элементы идут подряд сериями, серии — по возрастанию значения
            Arrays.sort(a);
            int best = a[0];
            int bestCount = 0;
            int k = 0;
            for (int i = 0; i < n; i++) {
                if (i > 0 && a[i] == a[i - 1]) {
                    k++;
                } else {
                    k = 1;
                }
                // Строгое > : при равной частоте остаётся более раннее, то есть меньшее значение
                if (k > bestCount) {
                    bestCount = k;
                    best = a[i];
                }
            }

            System.out.println(best);
        }
    }
}
