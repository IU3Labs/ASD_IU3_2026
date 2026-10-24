package ru.bmstu.lab01.task_1;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 1. Максимум и минимум.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 1. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        int min=a[0],max=a[0];
        for(int v:a){min=Math.min(min,v);
        max=Math.max(max,v);
        } System.out.println(min+" "+max);
    }
}
