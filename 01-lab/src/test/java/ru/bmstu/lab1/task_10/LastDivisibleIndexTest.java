package ru.bmstu.lab1.task_10;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LastDivisibleIndexTest {

    @Test
    void shouldFindLastIndexDivisibleByK() {
        int index = LastDivisibleIndex.findLastDivisibleIndex(new int[] {2, 3, 4, 5}, 2);

        assertEquals(2, index);
    }

    @Test
    void shouldReturnMinusOneWhenNothingIsDivisible() {
        int index = LastDivisibleIndex.findLastDivisibleIndex(new int[] {1, 3, 5}, 2);

        assertEquals(-1, index);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(LastDivisibleIndex::main, "4 2\n2 3 4 5\n");

        assertEquals("2", output);
    }
}
