package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Task25Test {

    @Test
    void shouldSumAllElements() {
        int[] array = {4, 7, 2, 9, 5};
        assertEquals(27L, Task25.sumAll(array));
    }

    @Test
    void shouldPreventIntegerOverflow() {
        int[] array = {Integer.MAX_VALUE, 0, Integer.MAX_VALUE};
        long expected = 2L * Integer.MAX_VALUE;
        assertEquals(expected, Task25.sumAll(array));
    }

    @Test
    void shouldWorkWithMaximumAllowedLength() {
        int[] array = new int[99_999];
        for (int i = 0; i < array.length; i++) {
            array[i] = 1;
        }
        assertEquals(99_999L, Task25.sumAll(array));
    }
}