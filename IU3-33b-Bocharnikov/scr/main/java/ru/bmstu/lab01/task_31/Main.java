package ru.bmstu.lab01.task_31;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 31. Скалярное произведение.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 31. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        long sum=0;
        for(int i=0;
        i<n;
        i++){sum+=(long)a[i]*sc.nextInt();
        }System.out.println(sum);
    }
}
