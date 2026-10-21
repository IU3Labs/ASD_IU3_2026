package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 25: Sum of elements in an array.
 * Calculates the total sum of all integer elements in the given array.
 */
public class Task25SumOfElements {

    /**
     * Calculates the sum of all elements in the array.
     * Uses long to prevent potential integer overflow for large arrays.
     *
     * @param array input array of integers
     * @return total sum of elements
     */
    public static long sumElements(int[] array) {
        if (array == null) {
            return 0;
        }

        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }