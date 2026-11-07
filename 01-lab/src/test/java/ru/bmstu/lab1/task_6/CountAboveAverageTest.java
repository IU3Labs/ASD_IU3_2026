package ru.bmstu.lab1.task_6;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountAboveAverageTest {

    @Test
    void shouldCountNumbersGreaterThanAverage() {
        int count = CountAboveAverage.countAboveAverage(new int[] {1, 2, 3, 6});

        assertEquals(1, count);
    }

    @Test
    void shouldReturnZeroWhenAllNumbersAreEqual() {
        int count = CountAboveAverage.countAboveAverage(new int[] {4, 4, 4, 4});

        assertEquals(0, count);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(CountAboveAverage::main, "4\n1 2 3 6\n");

        assertEquals("1", output);
    }
}
