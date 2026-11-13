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

        long result = sumOfSquares(array);
        System.out.println(result);
    }
}