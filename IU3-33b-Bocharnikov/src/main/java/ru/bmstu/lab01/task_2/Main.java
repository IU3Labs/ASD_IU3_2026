package ru.bmstu.lab01.task_2;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 2. Индекс первого вхождения.
 * Ввод: n, затем n целых чисел, затем дополнительный параметр.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 2. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        int x=sc.nextInt(),idx=-1;
        for(int i=0;
        i<n;
        i++){if(a[i]==x){idx=i;
        break;
        }}System.out.println(idx);
    }
}
