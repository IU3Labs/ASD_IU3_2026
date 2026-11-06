package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task27Test {
    @Test
    void shouldSumSquares() {
        int[] arr = {2, 3};
        long sum = 0;
        for (int x : arr) sum += x * x;
        assertEquals(13, sum);
    }
}