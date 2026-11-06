package ru.bmstu.lab1;
import java.util.Scanner;
/**
 * Задача 10. Индекс последнего элемента, кратного k.
 */
public class Task10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        int k = sc.nextInt();
        int lastIndex = -1;
        for (int i = 0; i < n; i++) {
            if (arr[i] % k == 0) lastIndex = i;
        }
        System.out.println(lastIndex);
        sc.close();
    }
}