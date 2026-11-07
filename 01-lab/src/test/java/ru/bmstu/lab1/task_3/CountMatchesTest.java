package ru.bmstu.lab1.task_3;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountMatchesTest {

    @Test
    void shouldCountHowManyTimes() {
        int count = CountMatches.countMatches(new int[] {2, 5, 2, 2, 7}, 2);

        assertEquals(3, count);
    }

    @Test
    void shouldReturnZeroWhenNumberIsMissing() {
        int count = CountMatches.countMatches(new int[] {1, 3, 4}, 2);

        assertEquals(0, count);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(CountMatches::main, "5 2\n2 5 2 2 7\n");

        assertEquals("3", output);
    }
}
