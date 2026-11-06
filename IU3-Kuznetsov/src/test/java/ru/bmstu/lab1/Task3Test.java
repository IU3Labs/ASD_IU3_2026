package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task3Test {
    @Test
    void shouldCountOccurrences() {
        int[] arr = {1, 2, 1, 3};
        int count = 0;
        for (int x : arr) if (x == 1) count++;
        assertEquals(2, count);
    }
}