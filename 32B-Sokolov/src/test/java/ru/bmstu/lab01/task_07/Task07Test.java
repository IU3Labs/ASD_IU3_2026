package ru.bmstu.lab01.task_07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task07Test {

    @Test
    void shouldFindSecondMax() {
        assertEquals(7, Task07.secondMax(new int[] {4, 9, 1, 7, 3}));
    }

    @Test
    void shouldWorkForIncreasingArray() {
        assertEquals(4, Task07.secondMax(new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    void shouldWorkForTwoElements() {
        assertEquals(2, Task07.secondMax(new int[] {5, 2}));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("20", TestUtils.runMain(Task07::main, "3\n10 30 20"));
    }
}
