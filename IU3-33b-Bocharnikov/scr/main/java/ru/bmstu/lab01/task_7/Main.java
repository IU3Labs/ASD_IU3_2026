package ru.bmstu.lab01.task_7;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 7. Второй максимум.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 7. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        int max=Integer.MIN_VALUE,second=Integer.MIN_VALUE;
        for(int v:a){if(v>max){second=max;
        max=v;
        }else if(v>second){second=v;
        }}System.out.println(second);
    }
}
