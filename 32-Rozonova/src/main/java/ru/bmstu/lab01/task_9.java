package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 9: Check if an array is sorted in non-decreasing order.
 * Determines whether the elements of an integer array are sorted in non-decreasing order.
 */
public class Task9CheckSorted {

    /**
     * Checks if the array is sorted in a non-decreasing order.
     * Iterates through the array and checks if any element is greater than the next one.
     *
     * @param array input array of integers
     * @return true if the array is sorted non-decreasing, false otherwise
     */
    public static boolean isSorted(int[] array) {
        if (array == null) {
            return true;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }

        return true;
    }
    