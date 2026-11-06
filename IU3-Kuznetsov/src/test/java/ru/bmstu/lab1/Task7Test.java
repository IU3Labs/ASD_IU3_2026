package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task7Test {
    @Test
    void shouldFindSecondMax() {
        int[] arr = {5, 1, 9, 2};
        int max = arr[0];
        for (int x : arr) if (x > max) max = x;
        int secondMax = arr[0];
        for (int x : arr) if (x > secondMax && x < max) secondMax = x;
        assertEquals(5, secondMax);
    }
}