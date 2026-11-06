package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task9Test {
    @Test
    void shouldCheckSorted() {
        int[] arr = {1, 2, 3};
        boolean isSorted = true;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) { isSorted = false; break; }
        }
        assertTrue(isSorted);
    }
}