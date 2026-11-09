package ru.bmstu.lab01.task_10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task10Test {

    @Test
    void shouldReturnLastMultiple() {
        assertEquals(3, Task10.lastMultiple(new int[] {3, 6, 7, 9, 10}, 3));
    }

    @Test
    void shouldReturnMinusOneWhenNoMultiples() {
        assertEquals(-1, Task10.lastMultiple(new int[] {1, 5, 7}, 2));
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        assertEquals(2, Task10.lastMultiple(new int[] {-8, 5, 0, 3}, -4));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("2", TestUtils.runMain(Task10::main, "4\n5 10 15 2\n5"));
    }
}
