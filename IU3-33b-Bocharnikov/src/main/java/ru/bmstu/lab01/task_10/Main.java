package ru.bmstu.lab01.task_10;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 10. Поиск последнего элемента, кратного k.
 * Ввод: n, затем n целых чисел, затем дополнительный параметр.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 10. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        int k=sc.nextInt(),idx=-1;
        for(int i=0;
        i<n;
        i++){if(a[i]%k==0)idx=i;
        }System.out.println(idx);
    }
}
