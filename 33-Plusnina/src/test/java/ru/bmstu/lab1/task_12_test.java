package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class task_12_test {
    @Test
    void shouldFindPair() {
        assertArrayEquals(new int[]{0, 3}, task_12.findPair(new int[]{1, 2, 3, 4, 5}, 5)); // 1+4=5
    }
    @Test
    void shouldReturnMinusOneWhenNoPair() {
        assertArrayEquals(new int[]{-1, -1}, task_12.findPair(new int[]{1, 2, 3}, 100));
    }
}