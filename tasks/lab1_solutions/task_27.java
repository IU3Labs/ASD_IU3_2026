// Задача 27. Сумма квадратов
// Вход: массив из n целых чисел.
// Выход: сумма квадратов всех элементов.
// Сложность: базовая.

import java.util.Scanner;

public class task_27 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long sumSq = 0;
        for (int i = 0; i < n; i++) {
            long num = sc.nextLong();
            sumSq += num * num;
        }
        System.out.println(sumSq);
    }
}