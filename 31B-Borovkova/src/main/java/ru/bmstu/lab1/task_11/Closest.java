
/*Задача 11. Ближайший к среднему
Вход: массив из (n) целых чисел.
Выход: элемент массива, ближайший к среднему арифметическому. Если несколько — вывести первый по индексу.
Ограничения: среднее может быть дробным.
Сложность: средняя */

package ru.bmstu.lab1.task_11;

import java.util.Scanner;

/**
 * Решение задачи 11: поиск элемента массива, ближайшего к среднему арифметическому.
 */
public class Closest {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Начальное значение суммы: пока не сложено ни одного элемента. */
    private static final int SUM_COUNT = 0;

    /**
     * Читает n, n целых чисел, печатает элемент, ближайший к среднему арифметическому.
     * Если таких несколько, печатает первый по индексу.
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
        for (int i = 0; i < n; i++) {
            s = s + a[i];
        }
        // Среднее может быть дробным: приводим сумму к double до деления
        double sr = (double) s / n;

        // best — индекс самого близкого к среднему элемента среди просмотренных.
        // Знак < (а не <=) сохраняет первый по индексу при одинаковом расстоянии.
        int best = 0;
        for (int i = 1; i < n; i++) {
            if (Math.abs(a[i] - sr) < Math.abs(a[best] - sr)) {
                best = i;
            }
        }

        System.out.println(a[best]);

        scanner.close();
    }
}