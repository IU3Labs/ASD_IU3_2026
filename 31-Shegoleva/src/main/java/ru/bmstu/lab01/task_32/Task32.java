package ru.bmstu.lab01.task_32;


import java.util.Scanner;

/**
 * Задача 32. Сумма произведений пар.
 * Вход: массив из (n) целых чисел.
 * Выход: сумма произведений всех пар элементов (a_i * a_j) для (i < j).
 * Требование: (O(n)) Сложность: высокая
 */


public class Task32 {

    /**
     * Подсчет суммы произведений пар в массиве.
     *
     * @param array массив целых чисел
     * @return сумма произведения пар элементов массива (тип long, чтобы избежать переполнения)
     */
    public static long calculateSumOfPairProduct(int[] array) {
        long result = 0;
        long prevSum = 0;
        for (int i = 0; i < array.length; i++) {
            result += array[i] * prevSum;
            prevSum += array[i];
        }
        return result;
    }

    /**
     * Читает с клавиатуры число n, а затем n целых чисел и выводит сумму произведений пар его элементов.
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        long result = calculateSumOfPairProduct(array);
        System.out.println(result);

    }

}
