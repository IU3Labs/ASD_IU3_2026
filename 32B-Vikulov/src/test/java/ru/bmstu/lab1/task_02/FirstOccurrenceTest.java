package ru.bmstu.lab1.task_02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class FirstOccurrenceTest {

    @Test
    void shouldReturnFirstIndexWhenElementRepeats() {
        assertEquals(1, FirstOccurrence.solve(new int[] {4, 7, 2, 7}, 7));
    }

    @Test
    void shouldReturnZeroWhenElementIsFirst() {
        assertEquals(0, FirstOccurrence.solve(new int[] {7, 1}, 7));
    }

    @Test
    void shouldReturnMinusOneWhenElementIsAbsent() {
        assertEquals(FirstOccurrence.NOT_FOUND, FirstOccurrence.solve(new int[] {1, 2, 3}, 9));
    }

    @Test
    void shouldPrintIndexFromConsole() {
        assertEquals("2", ConsoleRunner.run(FirstOccurrence::main, "5\n1 2 3 3 3\n3\n"));
    }
}
