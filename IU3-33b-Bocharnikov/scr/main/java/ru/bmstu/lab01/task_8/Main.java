package ru.bmstu.lab01.task_8;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 8. Фильтрация по знаку.
 * Ввод: n, затем n целых чисел.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 8. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        StringJoiner positive=new StringJoiner(" "),negative=new StringJoiner(" ");
        for(int v:a){if(v>0)positive.add(Integer.toString(v));
        else if(v<0)negative.add(Integer.toString(v));
        }System.out.println(positive);
        System.out.println(negative);
    }
}
