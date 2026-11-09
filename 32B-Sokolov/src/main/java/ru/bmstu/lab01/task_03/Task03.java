// Задача 3. Подсчёт вхождений.
// Вход: массив из n целых чисел и число x. Выход: количество элементов, равных x.
package ru.bmstu.lab01.task_03;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 3: сколько раз x встречается в массиве.
 */
public class Task03 {

    /**
     * Считает вхождения x.
     *
     * @param a массив
     * @param x что считаем
     * @return количество
     */
    public static int count(int[] a, int x) {
        int cnt = 0;
        for (int v : a) {
            if (v == x) {
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
        int x = in.nextInt();
        System.out.println(count(a, x));
    }
}
