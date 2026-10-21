package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 5. Сумма элементов на чётных позициях.
 * Вход: массив.
 * Выход: сумма arr[0], arr[2], ...
 */
public class Task05SumEvenIndices {
    /**
     * Суммирует элементы с чётными индексами.
     * @param arr массив
     * @return сумма
     */
    public static long sumEvenIndices(int[] arr) {
        long sum = 0;
        for (int i = 0; i < arr.length; i += 2) sum += arr[i];
        return sum;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        System.out.println(sumEvenIndices(arr));
    }
}