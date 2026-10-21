package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 7. Второй максимум.
 * Вход: массив с различными элементами.
 * Выход: второй по величине.
 */
public class Task07SecondMax {
    /**
     * Находит второй максимум за один проход.
     * @param arr массив
     * @return второй максимум
     */
    public static int secondMax(int[] arr) {
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        for (int val : arr) {
            if (val > max1) {
                max2 = max1;
                max1 = val;
            } else if (val > max2) {
                max2 = val;
            }
        }
        return max2;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        System.out.println(secondMax(arr));
    }
}