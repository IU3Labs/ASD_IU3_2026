package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task10Test {
    @Test
    void shouldFindLastMultiple() {
        int[] arr = {2, 4, 5, 6};
        int k = 2;
        int last = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % k == 0) last = i;
        }
        assertEquals(3, last);
    }
}