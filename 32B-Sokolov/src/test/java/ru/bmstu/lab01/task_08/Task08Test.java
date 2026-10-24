package ru.bmstu.lab01.task_08;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task08Test {

    @Test
    void shouldKeepOrderAndSkipZeros() {
        int[] a = {3, -1, 0, 5, -4, 0, 2};
        assertEquals(List.of(3, 5, 2), Task08.positives(a));
        assertEquals(List.of(-1, -4), Task08.negatives(a));
    }

    @Test
    void shouldReturnEmptyListsForZeros() {
        int[] a = {0, 0};
        assertTrue(Task08.positives(a).isEmpty());
        assertTrue(Task08.negatives(a).isEmpty());
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("7 1\n-2 -3", TestUtils.runMain(Task08::main, "5\n-2 7 0 -3 1"));
    }
}
