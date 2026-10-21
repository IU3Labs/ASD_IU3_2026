package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 27: Sum of squares of elements in an array.
 * Calculates the sum of squares of all integer elements in the given array.
 */
public class Task27SumOfSquares {

    /**
     * Calculates the sum of squares of all elements in the array.
     * Uses long to prevent arithmetic overflow during multiplication.
     *
     * @param array input array of integers
     * @return sum of squares of elements
     */
    public static long sumOfSquares(int[] array) {
        if (array == null) {
            return 0;
        }

        long sum = 0;
        for (int value : array) {
            sum += (long) value * value;
        }
        return sum;
    }