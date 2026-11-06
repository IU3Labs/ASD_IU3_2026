package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task5Test {
    @Test
    void shouldSumEvenPositions() {
        int[] arr = {1, 2, 3, 4, 5};
        int sum = 0;
        for (int i = 0; i < arr.length; i += 2) sum += arr[i];
        assertEquals(9, sum);
    }
}