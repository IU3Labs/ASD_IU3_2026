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

    /**
     * Main method for standard input/output execution.
     * Expects input: first line contains n and target x, second line contains n integers.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        int target = scanner.nextInt();

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        int index = findFirstIndex(array, target);
        System.out.println(index);
    }
}