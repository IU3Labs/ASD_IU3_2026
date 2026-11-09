package ru.bmstu.lab1.task_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class MinMaxTest {

    @Test
    void shouldFindMinAndMax() {
        int[] actual = MinMax.findMinMax(new int[] {3, -1, 7, 0, 7});
        assertArrayEquals(new int[] {-1, 7}, actual);
    }

    @Test
    void shouldFindMinAndMaxAtArrayEdges() {
        int[] actual = MinMax.findMinMax(new int[] {-5, 1, 2, 9});
        assertArrayEquals(new int[] {-5, 9}, actual);
    }

    @Test
    void shouldReturnSameValueWhenAllElementsEqual() {
        assertArrayEquals(new int[] {4, 4}, MinMax.findMinMax(new int[] {4, 4}));
    }

    @Test
    void shouldWorkWithIntegerLimits() {
        int[] actual = MinMax.findMinMax(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE});
        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}, actual);
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> MinMax.findMinMax(new int[0]));
    }

    @Test
    void shouldPrintMinAndMax() {
        assertEquals("-1 7", MainRunner.run(Main::run, "5\n3 -1 7 0 7"));
    }
}
