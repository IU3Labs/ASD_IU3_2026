package ru.bmstu.lab01.task_31;

import java.math.BigInteger;
import java.util.Scanner;

/** Задача 31. Найти скалярное произведение двух массивов. */
public class Main {

    /** Читает два массива и выводит скалярное произведение. */
    static void main() {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < n; i++) {
            long x = input.nextInt();
            sum = sum.add(BigInteger.valueOf(numbers[i] * x));
        }

        System.out.println(sum);
        input.close();
    }
}