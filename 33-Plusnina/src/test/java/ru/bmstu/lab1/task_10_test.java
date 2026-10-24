package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class task_10_test {
    @Test
    void shouldFindLastMultiple() {
        assertEquals(4, task_10.lastMultiple(new int[]{2, 5, 10, 7, 20}, 5));
    }
    @Test
    void shouldReturnMinusOneWhenNotFound() {
        assertEquals(-1, task_10.lastMultiple(new int[]{1, 2, 3}, 5));
    }
}