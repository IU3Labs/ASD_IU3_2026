package ru.bmstu.lab1.task_4;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AllIndexesTest {

    @Test
    void shouldFindEveryIndex() {
        int[] indexes = AllIndexes.findAllIndexes(new int[] {4, 1, 4, 2, 4}, 4);

        assertArrayEquals(new int[] {0, 2, 4}, indexes);
    }

    @Test
    void shouldReturnEmptyWhenNumberIsMissing() {
        int[] indexes = AllIndexes.findAllIndexes(new int[] {1, 2, 3}, 9);

        assertEquals(0, indexes.length);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(AllIndexes::main, "5 4\n4 1 4 2 4\n");

        assertEquals("0 2 4", output);
    }
}
