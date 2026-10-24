package ru.bmstu.lab01.task_26;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 26. Произведение элементов по модулю 1000000007.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 26. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        final long mod=1_000_000_007L;
        long product=1;
        for(int v:a){product=product*Math.floorMod((long)v,mod)%mod;
        }System.out.println(product);
    }
}
