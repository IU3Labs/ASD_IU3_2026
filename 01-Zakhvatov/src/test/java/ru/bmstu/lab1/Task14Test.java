package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task14Test {

    @Test
    void shouldSortInDescendingOrder() {
        int[] arr = {12, 11, 13, 5, 6};
        int[] expected = {13, 12, 11, 6, 5};
        Assertions.assertArrayEquals(expected, Task14.insertionSort(arr));
    }

    @Test
    void shouldMaintainAlreadyDescendingArray() {
        int[] arr = {9, 7, 5, 3, 1};
        int[] expected = {9, 7, 5, 3, 1};
        Assertions.assertArrayEquals(expected, Task14.insertionSort(arr));
    }

    @Test
    void shouldSortAscendingArrayToDescending() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {5, 4, 3, 2, 1};
        Assertions.assertArrayEquals(expected, Task14.insertionSort(arr));
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] arr = {2, 4, 2, 1, 4};
        int[] expected = {4, 4, 2, 2, 1};
        Assertions.assertArrayEquals(expected, Task14.insertionSort(arr));
    }

    @Test
    void shouldSortArrayWithNegativeNumbers() {
        int[] arr = {-10, 0, 5, -2, 8};
        int[] expected = {8, 5, 0, -2, -10};
        Assertions.assertArrayEquals(expected, Task14.insertionSort(arr));
    }

    @Test
    void shouldSortMinimalArraySize() {
        int[] arr = {3, 7};
        int[] expected = {7, 3};
        Assertions.assertArrayEquals(expected, Task14.insertionSort(arr));
    }
}
