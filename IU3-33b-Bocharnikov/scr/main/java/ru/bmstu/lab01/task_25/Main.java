package ru.bmstu.lab01.task_25;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 25. Сумма элементов.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 25. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        long sum=0;
        for(int v:a)sum+=v;
        System.out.println(sum);
    }
}
