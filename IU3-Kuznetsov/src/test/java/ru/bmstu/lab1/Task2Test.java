package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task2Test {
    @Test
    void shouldFindFirstIndex() {
        int[] arr = {4, 8, 15, 16};
        int target = 15;
        int result = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) { result = i; break; }
        }
        assertEquals(2, result);
    }
}