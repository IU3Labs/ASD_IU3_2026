package ru.bmstu.lab1;
import java.util.Scanner;
import java.util.Arrays;
/**
 * Задача 28. Медиана массива.
 */
public class Task28 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        Arrays.sort(arr);
        if (n % 2 != 0) {
            System.out.println(arr[n / 2]);
        } else {
            double median = (arr[n / 2 - 1] + arr[n / 2]) / 2.0;
            System.out.println(median);
        }
        sc.close();
    }
}