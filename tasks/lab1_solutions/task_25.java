// Задача 25. Сумма элементов
// Вход: массив из n целых чисел.
// Выход: сумма всех элементов.
// Сложность: базовая.

import java.util.Scanner;

public class task_25 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += sc.nextLong();
        }
        System.out.println(sum);
    }
}