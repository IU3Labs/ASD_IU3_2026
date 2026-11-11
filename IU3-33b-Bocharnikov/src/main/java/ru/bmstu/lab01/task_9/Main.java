package ru.bmstu.lab01.task_9;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 9. Проверка отсортированности.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 9. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        boolean sorted=true;
        for(int i=1;
        i<n;
        i++){if(a[i]<a[i-1]){sorted=false;
        break;
        }}System.out.println(sorted?"YES":"NO");
    }
}
