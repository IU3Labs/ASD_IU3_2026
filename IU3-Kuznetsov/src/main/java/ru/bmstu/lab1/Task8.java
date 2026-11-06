package ru.bmstu.lab1;
import java.util.Scanner;
/**
 * Задача 8. Фильтрация по знаку.
 */
public class Task8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        int posCount = 0, negCount = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] > 0) posCount++;
            else if (arr[i] < 0) negCount++;
        }
        int[] pos = new int[posCount];
        int[] neg = new int[negCount];
        int p = 0, ng = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] > 0) pos[p++] = arr[i];
            else if (arr[i] < 0) neg[ng++] = arr[i];
        }
        for (int x : pos) System.out.print(x + " ");
        System.out.println();
        for (int x : neg) System.out.print(x + " ");
        System.out.println();
        sc.close();
    }
}