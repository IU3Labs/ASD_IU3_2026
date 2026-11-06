package ru.bmstu.lab1;
import java.util.Scanner;
/**
 * Задача 7. Второй максимум.
 */
public class Task7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        int max = arr[0];
        for (int i = 1; i < n; i++) if (arr[i] > max) max = arr[i];
        int secondMax = arr[0];
        for (int i = 1; i < n; i++) if (arr[i] > secondMax && arr[i] < max) secondMax = arr[i];
        System.out.println(secondMax);
        sc.close();
    }
}