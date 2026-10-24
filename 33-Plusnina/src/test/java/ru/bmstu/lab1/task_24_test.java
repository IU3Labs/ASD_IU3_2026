package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class task_24_test {
    @Test
    void shouldSortEvenAscOddDesc() {
        assertArrayEquals(new int[]{2, 4, 5, 3, 1}, 
            task_24.sortEvenOdd(new int[]{1, 2, 3, 4, 5}));
    }
}