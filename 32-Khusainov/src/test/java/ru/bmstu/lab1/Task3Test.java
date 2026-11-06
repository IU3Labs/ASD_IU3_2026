package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Task3Test {

    @Test
    void shouldCountMultipleOccurrences() {
        int[] array = {4, 7, 2, 9, 2, 2, 5};
        assertEquals(3, Task3.countOccurrences(array, 2));
    }

    @Test
    void shouldReturnZeroWhenNotFound() {
        int[] array = {4, 7, 2, 9, 5};
        assertEquals(0, Task3.countOccurrences(array, 100));
    }

    @Test
    void shouldWorkWithMaximumAllowedLength() {
        int[] array = new int[99_999];
        array[0] = 42;
        array[50_000] = 42;
        array[array.length - 1] = 42;
        assertEquals(3, Task3.countOccurrences(array, 42));
    }
}