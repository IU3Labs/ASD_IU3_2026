package ru.bmstu.lab1.task_34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class MaxSubarrayTest {

    @Test
    void shouldFindMaxSumInTheMiddle() {
        // лучший подмассив [4, -1, 2, 1]
        assertEquals(6, MaxSubarray.maxSum(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    @Test
    void shouldReturnMaxElementWhenAllNegative() {
        assertEquals(-1, MaxSubarray.maxSum(new int[] {-3, -1, -2}));
    }

    @Test
    void shouldTakeWholeArrayWhenAllPositive() {
        assertEquals(10, MaxSubarray.maxSum(new int[] {1, 2, 3, 4}));
    }

    @Test
    void shouldNotOverflowOnBigNumbers() {
        int[] array = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        assertEquals(2L * Integer.MAX_VALUE, MaxSubarray.maxSum(array));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> MaxSubarray.maxSum(new int[0]));
    }

    @Test
    void shouldPrintMaxSum() {
        assertEquals("6", MainRunner.run(Main::run, "9\n-2 1 -3 4 -1 2 1 -5 4"));
    }
}
