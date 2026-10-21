package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 9. Проверка отсортированности.
 * Вход: массив.
 * Выход: YES если неубывающий, иначе NO.
 */
public class Task09CheckSorted {
    /**
     * Проверяет неубывание.
     * @param arr массив
     * @return true если отсортирован
     */
    public static boolean isNonDecreasing(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        System.out.println(isNonDecreasing(arr) ? "YES" : "NO");
    }
}