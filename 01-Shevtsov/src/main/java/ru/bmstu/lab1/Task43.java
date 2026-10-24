package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Решение задачи № 43: перемещение нулей в конец массива.
 */
public class Task43 {

    /**
     * Перемещает все нули в конец массива, сохраняя порядок ненулевых элементов.
     *
     * @param numbers массив целых чисел, который необходимо изменить
     */
    public static void moveZerosToEnd(int[] numbers) {
        int index = 0;

        for (int number : numbers) {
            if (number != 0) {
                numbers[index] = number;
                index++;
            }
        }

        while (index < numbers.length) {
            numbers[index] = 0;
            index++;
        }
    }

    /**
     * Считывает массив, перемещает нули в конец и выводит результат.
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

        moveZerosToEnd(numbers);

        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(numbers[i]);
        }

        System.out.println();
    }
}
