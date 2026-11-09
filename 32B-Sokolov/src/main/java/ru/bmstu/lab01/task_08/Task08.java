// Задача 8. Фильтрация по знаку.
// Вход: массив из n целых чисел. Выход: положительные и отрицательные элементы в исходном порядке, без нулей.
package ru.bmstu.lab01.task_08;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 8: разбить массив на положительные и отрицательные.
 */
public class Task08 {

    /**
     * Положительные элементы в исходном порядке.
     *
     * @param a массив
     * @return список положительных
     */
    public static List<Integer> positives(int[] a) {
        List<Integer> res = new ArrayList<>();
        for (int v : a) {
            if (v > 0) {
                res.add(v);
            }
        }
        return res;
    }

    /**
     * Отрицательные элементы в исходном порядке.
     *
     * @param a массив
     * @return список отрицательных
     */
    public static List<Integer> negatives(int[] a) {
        List<Integer> res = new ArrayList<>();
        for (int v : a) {
            if (v < 0) {
                res.add(v);
            }
        }
        return res;
    }

    private static String toLine(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int v : list) {
            sb.append(v).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        System.out.println(toLine(positives(a)));
        System.out.println(toLine(negatives(a)));
    }
}
