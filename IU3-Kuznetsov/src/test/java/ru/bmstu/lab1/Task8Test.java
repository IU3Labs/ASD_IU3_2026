package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task8Test {
    @Test
    void shouldFilterBySign() {
        int[] arr = {-1, 2, 0, -3, 4};
        int pos = 0, neg = 0;
        for (int x : arr) {
            if (x > 0) pos++;
            else if (x < 0) neg++;
        }
        assertEquals(2, pos);
        assertEquals(2, neg);
    }
}