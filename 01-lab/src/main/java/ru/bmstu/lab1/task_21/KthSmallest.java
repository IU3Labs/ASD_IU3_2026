package ru.bmstu.lab1.task_21;

import java.util.Arrays;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 21. K-й наименьший элемент.
 * Вход: массив из n целых чисел и число k (1 <= k <= n).
 * Выход: k-й наименьший элемент.
 * По условию массив нужно отсортировать и взять элемент с индексом k - 1.
 *
 * Ввод:
 * n k
 * a1 a2 ... an
 */

/**
 * Находит k-й минимум через сортировку.
 */
public class KthSmallest {

    /**
     * Сортирует массив и возвращает элемент с индексом k - 1.
     */
    public static int findKthSmallest(int[] numbers, int k) {
        // По условию достаточно отсортировать и взять нужный по счёту элемент.
        Arrays.sort(numbers);
        return numbers[k - 1];
    }

    /**
     * Читает массив и k, печатает k-й минимум.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int k = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(findKthSmallest(numbers, k));
    }
}
