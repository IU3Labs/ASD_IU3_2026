package ru.bmstu.lab1.task_8;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SplitBySignTest {

    @Test
    void shouldSeparatePositiveAndNegative() {
        int[][] parts = SplitBySign.splitBySign(new int[] {3, -1, 0, 4, -2});

        assertArrayEquals(new int[] {3, 4}, parts[0]);
        assertArrayEquals(new int[] {-1, -2}, parts[1]);
    }

    @Test
    void shouldReturnEmptyPositiveWhenAllAreNegative() {
        int[][] parts = SplitBySign.splitBySign(new int[] {-5, -1});

        assertEquals(0, parts[0].length);
        assertArrayEquals(new int[] {-5, -1}, parts[1]);
    }

    @Test
    void shouldReturnEmptyNegativeWhenAllArePositive() {
        int[][] parts = SplitBySign.splitBySign(new int[] {2, 8, 0});

        assertArrayEquals(new int[] {2, 8}, parts[0]);
        assertEquals(0, parts[1].length);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(SplitBySign::main, "5\n3 -1 0 4 -2\n");

        assertEquals("3 4\n-1 -2", output);
    }
}
