package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Решение задачи № 31: вычисление скалярного произведения двух массивов.
 */
public class Task31 {

    /**
     * Вычисляет скалярное произведение двух массивов одинаковой длины.
     *
     * @param first первый массив целых чисел
     * @param second второй массив целых чисел
     * @return сумма произведений соответствующих элементов массивов
     */
    public static long dotProduct(int[] first, int[] second) {
        long result = 0;

        for (int i = 0; i < first.length; i++) {
            result += (long) first[i] * second[i];
        }

        return result;
    }

    /**
     * Считывает два массива и выводит их скалярное произведение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] first = new int[n];
        int[] second = new int[n];

        for (int i = 0; i < n; i++) {
            first[i] = scanner.nextInt();
        }

        for (int i = 0; i < n; i++) {
            second[i] = scanner.nextInt();
        }

        System.out.println(dotProduct(first, second));
    }
}

