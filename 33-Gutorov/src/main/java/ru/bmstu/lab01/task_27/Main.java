package ru.bmstu.lab01.task_27;

import java.math.BigInteger;
import java.util.Scanner;

/** Задача 27. Найти сумму квадратов элементов массива. */
public class Main {

    /** Читает числа и выводит сумму их квадратов. */
    static void main() {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < n; i++) {
            long x = input.nextInt();
            sum = sum.add(BigInteger.valueOf(x * x));
        }

        System.out.println(sum);
        input.close();
    }
}