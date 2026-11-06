package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task25Test {
    @Test
    void shouldSumAll() {
        int[] arr = {1, 2, 3};
        long sum = 0;
        for (int x : arr) sum += x;
        assertEquals(6, sum);
    }
}