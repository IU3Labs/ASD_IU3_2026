// Задача 5. Сумма элементов на чётных позициях.
// Вход: массив из n целых чисел. Выход: сумма элементов с индексами 0, 2, 4, ...
package ru.bmstu.lab01.task_05;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 5: сумма на чётных индексах.
 */
public class Task05 {

    /**
     * Сумма элементов с чётными индексами.
     *
     * @param a массив
     * @return сумма
     */
    public static long sumEven(int[] a) {
        long sum = 0; // long, т.к. сумма int-ов может переполниться
        for (int i = 0; i < a.length; i += 2) {
            sum += a[i];
        }
        return sum;
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        System.out.println(sumEven(a));
    }
}
