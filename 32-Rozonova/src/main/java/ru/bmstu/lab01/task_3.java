package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 3: Count occurrences of an element in an array.
 * Counts how many times a given target value appears in an integer array.
 */
public class Task3CountOccurrences {

    /**
     * Counts the occurrences of a target value in the array.
     * Iterates through the array and increments a counter for each match.
     *
     * @param array input array of integers
     * @param target value to count
     * @return number of times target appears in the array
     */
    public static int countOccurrences(int[] array, int target) {
        if (array == null) {
            return 0;
        }

        int count = 0;
        for (int value : array) {
            if (value == target) {
                count++;
            }
        }
        
        return count;
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

        int result = countOccurrences(array, target);
        System.out.println(result);
    }
}