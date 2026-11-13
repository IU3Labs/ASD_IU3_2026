package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class Task13SelectionSortTest {

    @Test
    void should_sort_standard_unsorted_array() {
        int[] array = {64, 25, 12, 22, 11};
        int[] expected = {11, 12, 22, 25, 64};
        Task13SelectionSort.selectionSort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void should_keep_already_sorted_array() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        Task13SelectionSort.selectionSort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void should_sort_reverse_sorted_array() {
        int[] array = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        Task13SelectionSort.selectionSort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void should_handle_array_with_duplicates() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        int[] expected = {1, 1, 2, 3, 3, 4, 5, 5, 6, 9};
        Task13SelectionSort.selectionSort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void should_handle_single_element_array() {
        int[] array = {42};
        int[] expected = {42};
        Task13SelectionSort.selectionSort(array);
        assertArrayEquals(expected, array);
    }
}