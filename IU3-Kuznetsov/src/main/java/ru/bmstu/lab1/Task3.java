package ru.bmstu.lab1;
import java.util.Scanner;
/**
 * Задача 3. Подсчёт вхождений.
 */
public class Task3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        int target = sc.nextInt();
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == target) count++;
        }
        System.out.println(count);
        sc.close();
    }
}