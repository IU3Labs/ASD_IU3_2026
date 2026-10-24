package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task3Test {

    @Test
    void shouldCountMultipleOccurrences() {
        int[] arr = {1, 2, 3, 2, 5, 2};
        Assertions.assertEquals(3, Task3.countOccurrences(arr, 2));
    }

    @Test
    void shouldCountSingleOccurrence() {
        int[] arr = {10, 20, 30};
        Assertions.assertEquals(1, Task3.countOccurrences(arr, 20));
    }

    @Test
    void shouldReturnZeroWhenElementNotPresent() {
        int[] arr = {1, 2, 3, 4};
        Assertions.assertEquals(0, Task3.countOccurrences(arr, 99));
    }

    @Test
    void shouldCountWhenAllElementsMatch() {
        int[] arr = {7, 7, 7, 7};
        Assertions.assertEquals(4, Task3.countOccurrences(arr, 7));
    }
}
