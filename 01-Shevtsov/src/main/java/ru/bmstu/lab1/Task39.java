package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Решение задачи № 39: циклический сдвиг массива вправо.
 */
public class Task39 {

    /**
     * Выполняет циклический сдвиг массива вправо на k позиций.
     *
     * @param numbers массив целых чисел
     * @param k количество позиций сдвига
     */
    public static void rotateRight(int[] numbers, int k) {
        int n = numbers.length;

        if (n == 0) {
            return;
        }

        k = k % n;

        if (k == 0) {
            return;
        }

        reverse(numbers, 0, n - 1);
        reverse(numbers, 0, k - 1);
        reverse(numbers, k, n - 1);
    }

    private static void reverse(int[] numbers, int left, int right) {
        while (left < right) {
            int temp = numbers[left];
            numbers[left] = numbers[right];
            numbers[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * Считывает массив и величину сдвига, затем выводит результат.
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

        int k = scanner.nextInt();
        rotateRight(numbers, k);

        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(numbers[i]);
        }
        System.out.println();
    }
}
