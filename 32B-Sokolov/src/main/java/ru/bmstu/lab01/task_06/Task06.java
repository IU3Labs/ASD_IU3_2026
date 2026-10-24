// Задача 6. Элементы больше среднего.
// Вход: массив из n целых чисел (среднее целое). Выход: количество элементов строго больше среднего.
package ru.bmstu.lab01.task_06;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 6: сколько элементов больше среднего.
 */
public class Task06 {

    /**
     * Считает элементы больше среднего арифметического.
     *
     * @param a массив
     * @return количество
     */
    public static int countAboveAvg(int[] a) {
        long sum = 0;
        for (int v : a) {
            sum += v;
        }
        long avg = sum / a.length; // по условию делится нацело

        int cnt = 0;
        for (int v : a) {
            if (v > avg) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        System.out.println(countAboveAvg(a));
    }
}
