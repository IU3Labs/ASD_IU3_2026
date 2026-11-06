package ru.bmstu.lab1;
import java.util.Scanner;
/**
 * Задача 27. Сумма квадратов.
 */
public class Task27 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) {
            long x = sc.nextInt();
            sum += x * x;
        }
        System.out.println(sum);
        sc.close();
    }
}