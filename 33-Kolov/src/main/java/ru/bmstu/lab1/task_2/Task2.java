package ru.bmstu.lab1.task_2;

import java.util.Scanner;

/**
 * Задача 2. Индекс первого вхождения
 * Вход: массив из (n) целых чисел и число (x).
 * Выход: индекс первого элемента, равного (x). Если такого нет — вывести -1.
 * Сложность: базовая
 */

public class Task2 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            int x = scan.nextInt();
            int res = findFirstIndex(arr, x);
            System.out.println(res);
        }
    }

    //findFirstIndex находит индекс первого вхождения заданного числа.
    public static int findFirstIndex(int[] arr, int x) {
        sizeCheck(arr.length);

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                return i;
            }
        }
        return -1;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck (int size){
        if (size < 2 || size > 99999) {
            throw new IllegalArgumentException ("1 < array size < 100000");
        }
    }
}
