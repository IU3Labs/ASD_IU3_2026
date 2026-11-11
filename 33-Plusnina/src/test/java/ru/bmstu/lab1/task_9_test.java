package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class task_9_test {
    @Test
    void shouldReturnTrueForSorted() {
        assertTrue(task_9.isSorted(new int[]{1, 2, 2, 3, 4}));
    }
    @Test
    void shouldReturnFalseForUnsorted() {
        assertFalse(task_9.isSorted(new int[]{1, 3, 2, 4}));
    }
}