package ru.bmstu.lab01.task_6;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 6. Элементы больше среднего.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 6. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        long sum=0;
        for(int v:a)sum+=v;
        int count=0;
        for(int v:a){if((long)v*n>sum)count++;
        }System.out.println(count);
    }
}
