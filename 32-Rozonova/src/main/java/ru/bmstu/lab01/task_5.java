package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 5: Sum of elements at even positions.
 * Calculates the sum of array elements located at even indices (0, 2, 4, ...).
 */
public class Task5EvenPositionSum {

    /**
     * Calculates the sum of elements at even indices in the array.
     * Iterates through the array with a step of 2.
     *
     * @param array input array of integers
     * @return sum of elements at even positions
     */
    
    public static long sumEvenPositions(int[] array) {
        if (array == null) {
            return 0;
        }

        long sum = 0;
        for (int i = 0; i < array.length; i += 2) {
            sum += array[i];
        }
        return sum;
    }