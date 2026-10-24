package ru.bmstu.lab01.task_06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task06Test {

    @Test
    void shouldCountAboveAverage() {
        assertEquals(2, Task06.countAboveAvg(new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    void shouldReturnZeroWhenAllEqual() {
        assertEquals(0, Task06.countAboveAvg(new int[] {7, 7, 7}));
    }

    @Test
    void shouldWorkWithNegativeAverage() {
        assertEquals(1, Task06.countAboveAvg(new int[] {-6, -2, 2, -2}));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("2", TestUtils.runMain(Task06::main, "4\n0 10 0 10"));
    }
}
