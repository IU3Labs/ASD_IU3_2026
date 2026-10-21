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

    /**
     * Main method for standard input/output execution.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        boolean sorted = isSorted(array);
        if (sorted) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
