
package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 1: Minimum and Maximum elements in an array.
 * Finds both the minimum and maximum values in a given integer array in a single pass.
 */
public class Task1MaxMin {

    /**
     * A helper class to store the result containing both minimum and maximum values.
     */
    public static class Result {
        private final int min;
        private final int max;

        public Result(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }

    /**
     * Finds the minimum and maximum elements in an array of integers.
     * Iterates through the array once, updating min and max trackers.
     *
     * @param array input array of integers
     * @return Result object containing the minimum and maximum values
     */
    public static Result findMinMax(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int min = array[0];
        int max = array[0];

        // Traverse the array starting from the second element
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            } else if (array[i] > max) {
                max = array[i];
            }
        }

        return new Result(min, max);
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

        Result result = findMinMax(array);
        System.out.println("Min: " + result.getMin() + ", Max: " + result.getMax());
    }
}