package ru.bmstu.lab1.task25;

/**
 * Задача 25. Сумма элементов.
 * Вход: массив из n целых чисел.
 * Выход: сумма всех элементов.
 */
public class ArraySum {

    private ArraySum() {
    }

    /**
     * Считает сумму всех элементов массива.
     * Сумму храним в long: при n до 10^5 и элементах до 2^31
     * сумма может не поместиться в int.
     *
     * @param arr массив чисел
     * @return сумма элементов (0 для пустого массива)
     */
    public static long sum(int[] arr) {
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
