package ru.bmstu.lab01.task_3;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 3. Подсчёт вхождений.
 * Ввод: n, затем n целых чисел, затем дополнительный параметр.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 3. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        int x=sc.nextInt(),count=0;
        for(int v:a){if(v==x)count++;
        }System.out.println(count);
    }
}
