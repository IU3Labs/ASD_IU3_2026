package ru.bmstu.lab01.task_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Задача 4. Все индексы элемента.
 * Ввод: n, n целых чисел, затем x.
 * Вывод: индексы всех вхождений x в порядке возрастания через пробел.
 */
public class Main {
    public static List<Integer> solve(int[] a, int x) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < a.length; i++) if (a[i] == x) result.add(i);
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        int x = sc.nextInt();
        List<Integer> result = solve(a, x);
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(result.get(i));
        }
        System.out.println();
    }
}
