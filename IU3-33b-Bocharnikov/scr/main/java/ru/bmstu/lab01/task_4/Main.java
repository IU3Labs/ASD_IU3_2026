package ru.bmstu.lab01.task_4;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Задача 4. Все индексы элемента.
 * Ввод: n, затем n целых чисел, затем дополнительный параметр.
 * Вывод: результат согласно условию задачи.
 */
public class Main {
    /** Запускает решение задачи 4. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        int x=sc.nextInt();
        StringJoiner result=new StringJoiner(" ");
        for(int i=0;
        i<n;
        i++){if(a[i]==x)result.add(Integer.toString(i));
        }System.out.println(result);
    }
}
