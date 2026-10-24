// Задача 10. Поиск элемента с условием.
// Вход: массив из n целых чисел и число k != 0. Выход: индекс последнего элемента, кратного k, или -1.
package ru.bmstu.lab01.task_10;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 10: последний элемент, кратный k.
 */
public class Task10 {

    /**
     * Ищет последний элемент, который делится на k.
     *
     * @param a массив
     * @param k делитель, не 0
     * @return индекс или -1
     */
    public static int lastMultiple(int[] a, int k) {
        // идём с конца, первый найденный и есть последний
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] % k == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        int k = in.nextInt();
        System.out.println(lastMultiple(a, k));
    }
}
