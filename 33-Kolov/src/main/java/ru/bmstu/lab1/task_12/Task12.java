package ru.bmstu.lab1.task_12;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Задача 17. Сортировка по сумме цифр
 * Вход: массив из n неотрицательных целых чисел.
 * Выход: массив, отсортированный по возрастанию суммы цифр каждого числа. При равных суммах — сохранить исходный порядок.
 * Сложность: средняя
 */

public class Task12 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            sortByDigitSum(arr);

            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(arr[i]);
            }
            System.out.println();
        }
    }

    //sortByDigitSum сортирует элементы по сумме их цифр.
    public static void sortByDigitSum(int[] arr) {
        sizeCheck(arr.length);

        Integer[] temp = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                throw new IllegalArgumentException("Элементы массива должны быть неотрицательными.");
            }
            temp[i] = arr[i];
        }

        // Сортировка объектов устойчива: при равенстве сумм порядок сохраняется.
        Arrays.sort(temp, Task12::compareByDigitSum);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = temp[i];
        }
    }

    //compareByDigitSum сравнивает элементы по сумме цифр.
    private static int compareByDigitSum(int a, int b) {
        return Integer.compare(digitSum(a), digitSum(b));
    }

    //digitSum находит сумму цифр в элементе.
    private static int digitSum(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
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