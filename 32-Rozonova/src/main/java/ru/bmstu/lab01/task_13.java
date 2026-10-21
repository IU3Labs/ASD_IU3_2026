package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Task 13: Selection Sort implementation.
 * Sorts an integer array in non-decreasing order using the selection sort algorithm.
 */
public class Task13SelectionSort {
    /**
     * Sorts the given array in-place in non-decreasing order using selection sort.
     *
     * @param array input array of integers to be sorted
     */
    public static void selectionSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            
            // Find the index of the minimum element in the unsorted part
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element of the unsorted part
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }