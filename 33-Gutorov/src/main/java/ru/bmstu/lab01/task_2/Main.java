package ru.bmstu.lab01.task_2;

import java.util.Scanner;

/* Найти индекс первого вхождения числа. */
public class Main {

    /* Читаем массив и число, выводим индекс первого совпадения. */
    static void main() {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        int x = input.nextInt();
        int index = -1;

        for (int i = 0; i < n; i++) {
            if (numbers[i] == x) {
                index = i;
                break;
            }
        }

        System.out.println(index);
        input.close();
    }
}