package ru.bmstu.lab1.task_34;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class MaxSubarraySumTest {

    @Test
    void shouldFindClassicExample() {
        // подмассив [4, -1, 2, 1]
        assertEquals(6, MaxSubarraySum.solve(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    @Test
    void shouldReturnLargestElementWhenAllNegative() {
        assertEquals(-1, MaxSubarraySum.solve(new int[] {-3, -1, -2}));
    }

    @Test
    void shouldTakeWholeArrayWhenAllPositive() {
        assertEquals(6, MaxSubarraySum.solve(new int[] {1, 2, 3}));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        int max = Integer.MAX_VALUE;

        assertEquals(3L * max, MaxSubarraySum.solve(new int[] {max, max, max}));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> MaxSubarraySum.solve(new int[0]));
    }

    @Test
    void shouldPrintSumFromConsole() {
        assertEquals("5", ConsoleRunner.run(MaxSubarraySum::main, "3\n2 -1 4\n"));
    }
}
