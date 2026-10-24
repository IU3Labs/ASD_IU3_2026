package ru.bmstu.lab01.task_9;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task9Test {

    @Test
    void shouldReturnTrueForSortedArray() {
        int[] numbers = {1, 2, 3, 4};
        assertTrue(Task9.isSorted(numbers));
    }

    @Test
    void shouldReturnTrueWhenEqualNumbersExist() {
        int[] numbers = {1, 2, 2, 5};
        assertTrue(Task9.isSorted(numbers));
    }

    @Test
    void shouldReturnFalseForUnsortedArray() {
        int[] numbers = {1, 4, 3, 5};
        assertFalse(Task9.isSorted(numbers));
    }
}
