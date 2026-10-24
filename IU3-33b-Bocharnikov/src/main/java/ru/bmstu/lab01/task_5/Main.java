package ru.bmstu.lab01.task_5;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 5. Сумма элементов на чётных позициях.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 5. */
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
        i+=2)sum+=a[i];
        System.out.println(sum);
    }
}
