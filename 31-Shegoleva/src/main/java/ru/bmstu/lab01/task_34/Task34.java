package ru.bmstu.lab01.task_34;

import java.util.Scanner;

/**
 * Задача 34. Максимальная сумма подмассива.
 * Вход: массив из n целых чисел (могут быть отрицательные).
 * Выход: максимальная сумма непрерывного подмассива.
 * Требование: O(n) (алгоритм Кадане).
 */
public class Task34 {

    /**
     * Находит максимальную сумму непрерывного подмассива (алгоритм Кадане).
     *
     * @param array массив целых чисел, не пустой
     * @return максимальная сумма среди всех непрерывных подмассивов
     * @throws IllegalArgumentException если массив пустой
     */
    public static long maxSubarraySum(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        long current = array[0];
        long best = array[0];
        for (int i = 1; i < array.length; i++) {
            current = Math.max(array[i], current + array[i]);
            best = Math.max(best, current);
        }
        return best;
    }

    /**
     * Читает с клавиатуры число n, затем n целых чисел и выводит максимальную сумму подмассива.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        System.out.println(maxSubarraySum(array));
    }
}