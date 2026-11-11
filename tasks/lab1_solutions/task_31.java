// Задача 31. Скалярное произведение
// Вход: два массива одинаковой длины n.
// Выход: скалярное произведение.
// Сложность: базовая.

import java.util.Scanner;

public class task_31 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextLong();
        
        long dot = 0;
        for (int i = 0; i < n; i++) {
            long b = sc.nextLong();
            dot += a[i] * b;
        }
        System.out.println(dot);
    }
}