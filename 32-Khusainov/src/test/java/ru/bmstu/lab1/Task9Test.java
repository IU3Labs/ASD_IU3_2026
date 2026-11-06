package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Task9Test {

    @Test
    void shouldReturnYesForNonDecreasingArray() {
        int[] array = {1, 2, 2, 3, 4};
        assertEquals("YES", Task9.checkNonDecreasing(array));
    }

    @Test
    void shouldReturnNoForUnsortedArray() {
        int[] array = {1, 3, 2, 4};
        assertEquals("NO", Task9.checkNonDecreasing(array));
    }

    @Test
    void shouldWorkWithMaximumAllowedLength() {
        int[] array = new int[99_999];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertEquals("YES", Task9.checkNonDecreasing(array));
    }
}