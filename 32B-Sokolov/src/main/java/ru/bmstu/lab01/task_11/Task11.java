// Задача 11. Ближайший к среднему.
// Вход: массив из n целых чисел. Выход: элемент, ближайший к среднему (если несколько - первый по индексу).
package ru.bmstu.lab01.task_11;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 11: элемент, ближайший к среднему арифметическому.
 */
public class Task11 {

    /**
     * Ищет элемент с минимальным |a[i] - avg|.
     *
     * @param a массив
     * @return ближайший к среднему элемент
     */
    public static int closestToAvg(int[] a) {
        long sum = 0;
        for (int v : a) {
            sum += v;
        }
        double avg = (double) sum / a.length; // среднее может быть дробным

        int best = a[0];
        for (int i = 1; i < a.length; i++) {
            // строго меньше, чтобы при равенстве остался первый
            if (Math.abs(a[i] - avg) < Math.abs(best - avg)) {
                best = a[i];
            }
        }
        return best;
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        System.out.println(closestToAvg(a));
    }
}
