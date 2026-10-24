package ru.bmstu.lab1;

/**
 * Задача 16. Сортировка по модулю.
 */
public class Task16 {

    public static int[] sortByAbsoluteValue(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                long a = Math.abs((long) arr[j - 1]);
                long b = Math.abs((long) arr[j]);

                if (a > b || (a == b && arr[j - 1] > arr[j])) {
                    int tmp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = tmp;
                } else {
                    break;
                }
            }
        }
        return arr;
    }
}
