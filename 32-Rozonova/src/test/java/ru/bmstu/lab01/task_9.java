package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Task9CheckSortedTest {

    @Test
    void should_return_true_when_array_is_sorted_non_decreasing() {
        int[] array = {1, 2, 2, 3, 5};
        assertTrue(Task9CheckSorted.isSorted(array));
    }

    @Test
    void should_return_false_when_array_is_not_sorted() {
        int[] array = {1, 3, 2, 4};
        assertFalse(Task9CheckSorted.isSorted(array));
    }

    @Test
    void should_return_true_for_single_element_array() {
        int[] array = {42};
        assertTrue(Task9CheckSorted.isSorted(array));
    }

    @Test
    void should_return_true_for_empty_array() {
        int[] array = {};
        assertTrue(Task9CheckSorted.isSorted(array));
    }
}