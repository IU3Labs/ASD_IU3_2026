package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Task3CountOccurrencesTest {

    @Test
    void should_return_correct_count_when_element_appears_multiple_times() {
        int[] array = {1, 2, 3, 2, 4, 2, 5};
        int result = Task3CountOccurrences.countOccurrences(array, 2);
        assertEquals(3, result);
    }

    @Test
    void should_return_zero_when_element_not_found() {
        int[] array = {1, 2, 3, 4};
        int result = Task3CountOccurrences.countOccurrences(array, 99);
        assertEquals(0, result);
    }

    @Test
    void should_return_zero_for_empty_array() {
        int[] array = {};
        int result = Task3CountOccurrences.countOccurrences(array, 1);
        assertEquals(0, result);
    }

    @Test
    void should_return_full_length_when_all_elements_match() {
        int[] array = {5, 5, 5, 5};
        int result = Task3CountOccurrences.countOccurrences(array, 5);
        assertEquals(4, result);
    }
}