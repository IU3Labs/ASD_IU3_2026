package ru.bmstu.lab1.task_2;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstIndexTest {

    @Test
    void shouldFindFirstIndex() {
        int index = FirstIndex.findFirstIndex(new int[] {3, 5, 5, 1}, 5);

        assertEquals(1, index);
    }

    @Test
    void shouldFindNumberAtTheStart() {
        int index = FirstIndex.findFirstIndex(new int[] {4, 2, 9}, 4);

        assertEquals(0, index);
    }

    @Test
    void shouldReturnMinusOneWhenNumberIsMissing() {
        int index = FirstIndex.findFirstIndex(new int[] {1, 2, 3}, 8);

        assertEquals(-1, index);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(FirstIndex::main, "4 5\n3 5 5 1\n");

        assertEquals("1", output);
    }
}
