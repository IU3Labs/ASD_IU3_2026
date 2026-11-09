package ru.bmstu.lab01.task_02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task02Test {

    @Test
    void shouldReturnFirstIndexWhenSeveralMatches() {
        assertEquals(1, Task02.firstIndex(new int[] {5, 3, 7, 3}, 3));
    }

    @Test
    void shouldReturnMinusOneWhenNotFound() {
        assertEquals(-1, Task02.firstIndex(new int[] {1, 2, 3}, 10));
    }

    @Test
    void shouldFindLastElement() {
        assertEquals(2, Task02.firstIndex(new int[] {8, 1, 2}, 2));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("1", TestUtils.runMain(Task02::main, "5\n4 -1 6 -1 0\n-1"));
    }
}
