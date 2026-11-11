// Задача 36. Бегущая сумма
// Вход: массив из n целых чисел.
// Выход: массив той же длины, где i-й элемент равен сумме первых i + 1 элементов исходного массива.
// Сложность: базовая.

import java.util.Scanner;

public class task_36 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long currentSum = 0;
        for (int i = 0; i < n; i++) {
            currentSum += sc.nextLong();
            System.out.print(currentSum + " ");
        }
        System.out.println();
    }
}