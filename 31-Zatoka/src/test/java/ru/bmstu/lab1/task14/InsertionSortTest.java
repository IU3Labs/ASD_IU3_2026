package ru.bmstu.lab1.task14;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class InsertionSortTest {

    @Test
    void shouldSortUnsortedArrayDescending() {
        int[] arr = {5, -2, 9, 0, 3};
        int[] expected = {9, 5, 3, 0, -2};
        InsertionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldKeepArraySortedDescending() {
        int[] arr = {4, 3, 2, 1};
        int[] expected = {4, 3, 2, 1};
        InsertionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldReverseArraySortedAscending() {
        int[] arr = {1, 2, 3, 4};
        int[] expected = {4, 3, 2, 1};
        InsertionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] arr = {2, 3, 1, 3, 2};
        int[] expected = {3, 3, 2, 2, 1};
        InsertionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldNotChangeSingleElement() {
        int[] arr = {7};
        int[] expected = {7};
        InsertionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldNotFailOnEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        InsertionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}
