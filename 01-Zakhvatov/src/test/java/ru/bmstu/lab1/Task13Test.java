package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task13Test {

    @Test
    void shouldSortRegularUnsortedArray() {
        int[] arr = {64, 25, 12, 22, 11};
        int[] expected = {11, 12, 22, 25, 64};
        Assertions.assertArrayEquals(expected, Task13.selectionSort(arr));
    }

    @Test
    void shouldMaintainAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        Assertions.assertArrayEquals(expected, Task13.selectionSort(arr));
    }

    @Test
    void shouldSortReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        Assertions.assertArrayEquals(expected, Task13.selectionSort(arr));
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] arr = {3, 1, 3, 2, 1};
        int[] expected = {1, 1, 2, 3, 3};
        Assertions.assertArrayEquals(expected, Task13.selectionSort(arr));
    }

    @Test
    void shouldSortArrayWithNegativeNumbers() {
        int[] arr = {-5, 10, -20, 0, 3};
        int[] expected = {-20, -5, 0, 3, 10};
        Assertions.assertArrayEquals(expected, Task13.selectionSort(arr));
    }

    @Test
    void shouldSortMinimalArraySize() {
        int[] arr = {9, 2};
        int[] expected = {2, 9};
        Assertions.assertArrayEquals(expected, Task13.selectionSort(arr));
    }
}
