package ru.bmstu.lab01.task_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task3Test {

    @Test
    void shouldCountOccurrences() {
        int[] numbers = {2, 5, 2, 2, 7};
        int actualCount = Task3.countOccurrences(numbers, 2);
        assertEquals(3, actualCount);
    }

    @Test
    void shouldReturnZeroWhenNumberIsMissing() {
        int[] numbers = {1, 3, 5};
        int actualCount = Task3.countOccurrences(numbers, 2);
        assertEquals(0, actualCount);
    }

    @Test
    void shouldCountAllElements() {
        int[] numbers = {4, 4, 4};
        int actualCount = Task3.countOccurrences(numbers, 4);
        assertEquals(3, actualCount);
    }
}
