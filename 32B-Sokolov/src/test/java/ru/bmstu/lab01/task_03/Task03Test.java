package ru.bmstu.lab01.task_03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task03Test {

    @Test
    void shouldCountOccurrences() {
        assertEquals(3, Task03.count(new int[] {2, 5, 2, 2, 7}, 2));
    }

    @Test
    void shouldReturnZeroWhenNotFound() {
        assertEquals(0, Task03.count(new int[] {1, 2, 3}, 4));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("3", TestUtils.runMain(Task03::main, "6\n0 1 0 1 0 1\n0"));
    }
}
