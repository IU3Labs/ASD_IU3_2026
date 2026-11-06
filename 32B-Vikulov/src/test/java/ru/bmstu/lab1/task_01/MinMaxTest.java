package ru.bmstu.lab1.task_01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class MinMaxTest {

    @Test
    void shouldFindMinAndMaxInMixedArray() {
        assertArrayEquals(new int[] {-7, 9}, MinMax.solve(new int[] {3, -7, 9, 0}));
    }

    @Test
    void shouldReturnSameValueWhenAllElementsEqual() {
        assertArrayEquals(new int[] {5, 5}, MinMax.solve(new int[] {5, 5, 5}));
    }

    @Test
    void shouldHandleIntegerBounds() {
        int[] values = {Integer.MAX_VALUE, Integer.MIN_VALUE};

        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}, MinMax.solve(values));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> MinMax.solve(new int[0]));
    }

    @Test
    void shouldPrintMinAndMaxFromConsole() {
        assertEquals("1 4", ConsoleRunner.run(MinMax::main, "4\n4 1 3 2\n"));
    }
}
