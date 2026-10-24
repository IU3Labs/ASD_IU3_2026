package ru.bmstu.lab01.task_11;

import java.util.Scanner;

/**
 * Задача 11. Ближайший к среднему.
 * Ввод: n, затем n целых чисел.
 * Вывод: элемент, ближайший к среднему арифметическому;
 * при равенстве расстояний выбирается первый по индексу.
 */
public class Main {
    public static int solve(int[] a) {
        long sum = 0;
        for (int value : a) sum += value;
        int best = a[0];
        long bestDistance = Math.abs((long) a[0] * a.length - sum);
        for (int i = 1; i < a.length; i++) {
            long distance = Math.abs((long) a[i] * a.length - sum);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = a[i];
            }
        }
        return best;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        System.out.println(solve(a));
    }
}
