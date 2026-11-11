package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class task_5_test {
    @Test
    void shouldSumEvenIndices() {
        assertEquals(16L, task_5.sumEvenIndices(new int[]{1, 2, 3, 4, 5, 6, 7})); // 1+3+5+7
    }
    @Test
    void shouldHandleEmpty() {
        assertEquals(0L, task_5.sumEvenIndices(new int[]{}));
    }
}