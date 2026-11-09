package ru.bmstu.lab01.task_09;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task09Test {

    @Test
    void shouldAcceptEqualNeighbours() {
        assertTrue(Task09.isSorted(new int[] {1, 2, 2, 3}));
    }

    @Test
    void shouldRejectWhenBrokenAtEnd() {
        assertFalse(Task09.isSorted(new int[] {1, 2, 3, 0}));
    }

    @Test
    void shouldPrintYes() {
        assertEquals("YES", TestUtils.runMain(Task09::main, "3\n1 1 2"));
    }

    @Test
    void shouldPrintNo() {
        assertEquals("NO", TestUtils.runMain(Task09::main, "3\n2 1 3"));
    }
}
