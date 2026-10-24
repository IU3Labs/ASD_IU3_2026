// Задача 7. Второй максимум.
// Вход: массив из n различных целых чисел. Выход: второй по величине элемент.
package ru.bmstu.lab01.task_07;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 7: второй максимум за один проход.
 */
public class Task07 {

    /**
     * Ищет второй по величине элемент.
     *
     * @param a массив минимум из двух разных элементов
     * @return второй максимум
     */
    public static int secondMax(int[] a) {
        // берём первые два элемента, а не Integer.MIN_VALUE - он может быть в массиве
        int max = Math.max(a[0], a[1]);
        int second = Math.min(a[0], a[1]);
        for (int i = 2; i < a.length; i++) {
            if (a[i] > max) {
                second = max;
                max = a[i];
            } else if (a[i] > second) {
                second = a[i];
            }
        }
        return second;
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        System.out.println(secondMax(a));
    }
}
