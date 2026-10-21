package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 2: First occurrence index in an array.
 * Searches for the first occurrence of a target element in an integer array.
 */
public class Task2FirstIndex {

    /**
     * Finds the 0-based index of the first occurrence of x in the array.
     * Performs a linear search from left to right.
     *
     * @param array input array of integers
     * @param target value to search for
     * @return 0-based index of first occurrence, or -1 if not found
     */
    public static int findFirstIndex(int[] array, int target) {
        if (array == null) {
            return -1;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i; // Return immediately upon finding the first match
            }
        }

        return -1;
    }