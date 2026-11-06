package ru.bmstu.lab1;
import java.util.Scanner;
/**
 * Задача 25. Сумма элементов.
 */
public class Task25 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) sum += sc.nextInt();
        System.out.println(sum);
        sc.close();
    }
}