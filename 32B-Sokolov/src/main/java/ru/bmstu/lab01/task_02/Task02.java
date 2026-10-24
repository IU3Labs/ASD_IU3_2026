// Задача 2. Индекс первого вхождения.
// Вход: массив из n целых чисел и число x. Выход: индекс первого элемента, равного x, или -1.
package ru.bmstu.lab01.task_02;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 2: индекс первого вхождения x.
 */
public class Task02 {

    /**
     * Ищет первое вхождение x.
     *
     * @param a массив
     * @param x что ищем
     * @return индекс или -1
     */
    public static int firstIndex(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) {
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
        int x = in.nextInt();
        System.out.println(firstIndex(a, x));
    }
}
