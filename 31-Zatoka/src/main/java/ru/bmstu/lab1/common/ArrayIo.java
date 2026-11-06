package ru.bmstu.lab1.common;

import java.util.Scanner;

/**
 * Общий ввод и вывод массивов для всех задач лабораторной.
 * Вынесен отдельно, чтобы не повторять один и тот же код в каждом Main.
 */
public class ArrayIo {

    private ArrayIo() {
    }

    /**
     * Читает массив в формате: сначала число n, затем n целых чисел.
     *
     * @param scanner источник ввода
     * @return прочитанный массив длины n
     */
    public static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        return arr;
    }

    /**
     * Склеивает элементы массива в одну строку через пробел.
     *
     * @param arr массив чисел
     * @return строка вида "1 2 3" (пустая строка для пустого массива)
     */
    public static String toLine(int[] arr) {
        return toLine(arr, arr.length);
    }

    /**
     * Склеивает через пробел только первые count элементов массива.
     * Нужно, когда решение работает на месте и полезна только начальная часть массива.
     * Используется StringBuilder, чтобы при большом n не собирать
     * строку медленным сложением в цикле.
     *
     * @param arr массив чисел
     * @param count сколько первых элементов вывести (от 0 до arr.length)
     * @return строка вида "1 2 3" (пустая строка, если count равен 0)
     */
    public static String toLine(int[] arr, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }
}
