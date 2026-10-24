package ru.bmstu.lab01.task_04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task04Test {

    @Test
    void shouldReturnAllIndicesInOrder() {
        assertEquals(List.of(0, 2, 4), Task04.allIndices(new int[] {7, 1, 7, 3, 7}, 7));
    }

    @Test
    void shouldReturnEmptyListWhenNotFound() {
        assertTrue(Task04.allIndices(new int[] {1, 2, 3}, 5).isEmpty());
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("1 3", TestUtils.runMain(Task04::main, "5\n1 2 1 2 1\n2"));
    }
}
