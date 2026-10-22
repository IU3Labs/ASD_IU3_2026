package ru.bmstu.lab01.task_34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.common.ConsoleRunner;

class MaxSubarraySumTest {

    @Test
    void shouldFindMaxSumInMixedArray() {
        // подмассив [4, -1, 2, 1]
        assertEquals(6, MaxSubarraySum.maxSum(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    @Test
    void shouldReturnLargestElementWhenAllNegative() {
        assertEquals(-1, MaxSubarraySum.maxSum(new int[] {-8, -3, -1, -6}));
    }

    @Test
    void shouldTakeWholeArrayWhenAllPositive() {
        assertEquals(10, MaxSubarraySum.maxSum(new int[] {1, 2, 3, 4}));
    }

    @Test
    void shouldNotOverflowOnLargeSum() {
        int[] array = new int[10];
        Arrays.fill(array, Integer.MAX_VALUE);

        assertEquals(10L * Integer.MAX_VALUE, MaxSubarraySum.maxSum(array));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> MaxSubarraySum.maxSum(new int[0]));
    }

    @Test
    void shouldReadArrayAndPrintMaxSum() {
        // подмассив [2, -1, 3]
        assertEquals("4", ConsoleRunner.run(MaxSubarraySum::run, "4\n2 -1 3 -10\n"));
    }
}
