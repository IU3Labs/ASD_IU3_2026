package ru.bmstu.lab01.task_11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task11Test {

    @Test
    void shouldWorkWithFractionalAverage() {
        // среднее 7/3
        assertEquals(2, Task11.closestToAvg(new int[] {0, 2, 5}));
    }

    @Test
    void shouldReturnFirstWhenTie() {
        // среднее 2.5, 3 и 2 на одинаковом расстоянии
        assertEquals(3, Task11.closestToAvg(new int[] {3, 2, 1, 4}));
    }

    @Test
    void shouldWorkWithNegativeAverage() {
        assertEquals(-3, Task11.closestToAvg(new int[] {-10, -3, 1}));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("4", TestUtils.runMain(Task11::main, "3\n1 4 10"));
    }
}
