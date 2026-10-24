package ru.bmstu.lab01.util;

import java.util.Scanner;

/**
 * Чтение и вывод массивов, общее для всех задач.
 */
public final class ArrayIO {

    private ArrayIO() {
    }

    /**
     * Читает n, а затем n целых чисел.
     *
     * @param scanner откуда читать
     * @return массив
     */
    public static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        return a;
    }

    /**
     * Переводит массив в строку через пробел.
     *
     * @param a массив
     * @return строка
     */
    public static String join(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(a[i]);
        }
        return sb.toString();
    }
}
