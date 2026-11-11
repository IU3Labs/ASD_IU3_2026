package ru.bmstu.lab01.task_36;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 36. Бегущая сумма.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 36. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        long sum=0;
        StringJoiner result=new StringJoiner(" ");
        for(int v:a){sum+=v;
        result.add(Long.toString(sum));
        }System.out.println(result);
    }
}
