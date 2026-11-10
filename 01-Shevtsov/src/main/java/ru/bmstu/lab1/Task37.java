package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Решение задачи № 37: разворот массива на месте.
 */
public class Task37 {

    /**
     * Разворачивает массив, меняя порядок его элементов на обратный.
     *
     * @param numbers массив целых чисел, который необходимо развернуть
     */
    public static void reverseInPlace(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int temp = numbers[left];
            numbers[left] = numbers[right];
            numbers[right] = temp;

            left++;
            right--;
        }
    }

    /**
     * Считывает массив, разворачивает его и выводит результат.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        reverseInPlace(numbers);

        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(numbers[i]);
        }
        System.out.println();
    }
}
