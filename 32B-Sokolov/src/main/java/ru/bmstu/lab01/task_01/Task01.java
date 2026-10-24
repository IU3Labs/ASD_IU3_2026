// Задача 1. Максимум и минимум.
// Вход: массив из n целых чисел. Выход: минимальный и максимальный элементы массива.
package ru.bmstu.lab01.task_01;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 1: минимум и максимум массива.
 */
public class Task01 {

    /**
     * Ищет минимум и максимум.
     *
     * @param a массив
     * @return {min, max}
     */
    public static int[] minMax(int[] a) {
        int min = a[0];
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
            if (a[i] > max) {
                max = a[i];
            }
        }
        return new int[] {min, max};
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        int[] res = minMax(a);
        System.out.println(res[0] + " " + res[1]);
    }
}
