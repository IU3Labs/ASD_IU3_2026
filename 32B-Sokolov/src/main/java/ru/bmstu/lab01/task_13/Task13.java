// Задача 13. Сортировка выбором.
// Вход: массив из n целых чисел. Выход: массив, отсортированный по неубыванию.
// Без встроенных сортировок.
package ru.bmstu.lab01.task_13;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 13: сортировка выбором.
 */
public class Task13 {

    /**
     * Сортирует массив по неубыванию на месте.
     *
     * @param a массив
     */
    public static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            // ищем минимум в неотсортированной части [i, n) и ставим его на место i
            int minIdx = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIdx]) {
                    minIdx = j;
                }
            }
            int tmp = a[i];
            a[i] = a[minIdx];
            a[minIdx] = tmp;
        }
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        selectionSort(a);
        System.out.println(ArrayIO.join(a));
    }
}
