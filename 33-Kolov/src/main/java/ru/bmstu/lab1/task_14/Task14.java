package ru.bmstu.lab1.task_14;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Задача 27. Сумма квадратов
 * Вход: массив из n целых чисел.
 * Выход: сумма квадратов всех элементов.
 * Сложность: базовая
 */

public class Task14 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            BigInteger res = sumSquares(arr);
            System.out.println(res);
        }
    }

    //sumSquares находит сумму квадратов элементов массива.
    // Сумма квадратов может превысить диапазон long, поэтому используем BigInteger.
    public static BigInteger sumSquares(int[] arr) {
        sizeCheck(arr.length);

        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < arr.length; i++) {
            // Квадрат любого int помещается в long, но сумма квадратов — не всегда.
            long square = (long) arr[i] * arr[i];
            sum = sum.add(BigInteger.valueOf(square));
        }

        return sum;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99_999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}