package ru.bmstu.lab1.task_31;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 31. Скалярное произведение.
 * Вход: два массива одинаковой длины n.
 * Выход: скалярное произведение.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 * b1 b2 ... bn
 */

/**
 * Считает скалярное произведение двух массивов.
 */
public class ScalarProduct {

    /**
     * Возвращает сумму попарных произведений a[i] * b[i].
     */
    public static long scalarProduct(int[] first, int[] second) {
        long sum = 0;
        for (int i = 0; i < first.length; i++) {
            sum = sum + (long) first[i] * second[i];
        }
        return sum;
    }

    /**
     * Читает два массива и печатает их скалярное произведение.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] first = ArrayIO.readArray(scanner, length);
        int[] second = ArrayIO.readArray(scanner, length);
        System.out.println(scalarProduct(first, second));
    }
}
