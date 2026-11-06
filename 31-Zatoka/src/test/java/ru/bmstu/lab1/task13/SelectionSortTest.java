package ru.bmstu.lab1.task13;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class SelectionSortTest {

    @Test
    void shouldSortUnsortedArray() {
        int[] arr = {5, -2, 9, 0, 3};
        int[] expected = {-2, 0, 3, 5, 9};
        SelectionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldKeepSortedArray() {
        int[] arr = {1, 2, 3, 4};
        int[] expected = {1, 2, 3, 4};
        SelectionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortReversedArray() {
        int[] arr = {4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4};
        SelectionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] arr = {3, 1, 3, 1, 2};
        int[] expected = {1, 1, 2, 3, 3};
        SelectionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldNotChangeSingleElement() {
        int[] arr = {7};
        int[] expected = {7};
        SelectionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void shouldNotFailOnEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        SelectionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}
