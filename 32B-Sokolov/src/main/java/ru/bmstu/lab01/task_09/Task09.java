// Задача 9. Проверка отсортированности.
// Вход: массив из n целых чисел. Выход: YES, если массив неубывающий, иначе NO.
package ru.bmstu.lab01.task_09;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 9: проверка, что массив неубывающий.
 */
public class Task09 {

    /**
     * Проверяет, что каждый элемент не меньше предыдущего.
     *
     * @param a массив
     * @return true, если неубывающий
     */
    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        System.out.println(isSorted(a) ? "YES" : "NO");
    }
}
