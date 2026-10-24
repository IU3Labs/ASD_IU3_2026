package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Task2Test {

    @Test
    void shouldReturnFirstOccurrenceIndexForDuplicates() {
        int[] array = {4, 7, 2, 9, 2, 5};
        assertEquals(2, Task2.findFirstOccurrence(array, 2));
    }

    @Test
    void shouldReturnMinusOneWhenNotFound() {
        int[] array = {4, 7, 2, 9, 5};
        assertEquals(-1, Task2.findFirstOccurrence(array, 100));
    }

    @Test
    void shouldWorkWithMaximumAllowedLength() {
        int[] array = new int[99_999];
        array[50_000] = 42;
        assertEquals(50_000, Task2.findFirstOccurrence(array, 42));
    }
}