package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 26: Product of elements in an array modulo 10^9 + 7.
 * Calculates the total product of all integer elements in the given array modulo 10^9 + 7.
 */
public class Task26ProductOfElements {

    private static final long MOD = 1_000_000_007L;

    /**
     * Calculates the product of all elements in the array modulo 10^9 + 7.
     * Handles negative numbers and prevents arithmetic overflow using long.
     *
     * @param array input array of integers
     * @return product of elements modulo 10^9 + 7
     */
    public static long productElements(int[] array) {
        if (array == null || array.length == 0) {
            return 1; // Empty product is conventionally 1
        }

        long product = 1L;
        for (int value : array) {
            long val = value % MOD;
            if (val < 0) {
                val += MOD; // Handle negative numbers
            }
            product = (product * val) % MOD;
        }
        return product;
    }