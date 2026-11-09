package ru.bmstu.lab01.task_01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task01Test {

    @Test
    void shouldFindMinAndMax() {
        assertArrayEquals(new int[] {-7, 10}, Task01.minMax(new int[] {3, -7, 10, 0, 5}));
    }

    @Test
    void shouldWorkWhenAllElementsEqual() {
        assertArrayEquals(new int[] {4, 4}, Task01.minMax(new int[] {4, 4, 4}));
    }

    @Test
    void shouldWorkWithIntLimits() {
        int[] a = {0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}, Task01.minMax(a));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("-1 8", TestUtils.runMain(Task01::main, "4\n2 8 -1 5"));
    }
}
