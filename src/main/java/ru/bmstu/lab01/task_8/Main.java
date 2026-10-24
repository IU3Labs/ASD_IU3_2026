package ru.bmstu.lab01.task_8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Задача 8. Фильтрация по знаку.
 * Ввод: n, затем n целых чисел.
 * Вывод: положительные числа в исходном порядке на первой строке,
 * отрицательные числа в исходном порядке на второй строке. Нули пропускаются.
 */
public class Main {
    public static List<List<Integer>> solve(int[] a) {
        List<Integer> positive = new ArrayList<>();
        List<Integer> negative = new ArrayList<>();
        for (int value : a) {
            if (value > 0) positive.add(value);
            else if (value < 0) negative.add(value);
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(positive);
        result.add(negative);
        return result;
    }

    private static void printLine(List<Integer> values) {
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(values.get(i));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        List<List<Integer>> result = solve(a);
        printLine(result.get(0));
        printLine(result.get(1));
    }
}
