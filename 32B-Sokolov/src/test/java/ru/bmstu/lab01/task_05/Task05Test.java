package ru.bmstu.lab01.task_05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task05Test {

    @Test
    void shouldSumEvenIndicesOddLength() {
        assertEquals(9, Task05.sumEven(new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    void shouldSumEvenIndicesEvenLength() {
        assertEquals(40, Task05.sumEven(new int[] {10, 20, 30, 40}));
    }

    @Test
    void shouldNotOverflow() {
        int m = Integer.MAX_VALUE;
        assertEquals(3L * m, Task05.sumEven(new int[] {m, 0, m, 0, m}));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("10", TestUtils.runMain(Task05::main, "3\n4 0 6"));
    }
}
