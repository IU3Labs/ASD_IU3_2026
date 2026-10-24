package ru.bmstu.lab1;

/**
 * Задача 26. Произведение элементов по модулю 10^9 + 7.
 */
public class Task26 {

    public static long product(int[] arr) {
        long p = 1;
        long mod = 1_000_000_007L;

        for (int i = 0; i < arr.length; i++) {
            p = (p * arr[i]) % mod;
            if (p < 0) {
                p += mod;
            }
        }

        return p;
    }
}
