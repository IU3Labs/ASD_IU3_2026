// Задача 26. Произведение элементов
// Вход: массив из n целых чисел.
// Выход: произведение всех элементов по модулю 10^9 + 7.
// Сложность: базовая.

import java.util.Scanner;

public class task_26 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long prod = 1;
        long MOD = 1000000007L;
        for (int i = 0; i < n; i++) {
            long num = sc.nextLong();
            prod = (prod * ((num % MOD + MOD) % MOD)) % MOD;
        }
        System.out.println(prod);
    }
}