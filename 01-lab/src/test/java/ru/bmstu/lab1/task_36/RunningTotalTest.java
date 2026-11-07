package ru.bmstu.lab1.task_36;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RunningTotalTest {

    @Test
    void shouldBuildRunningTotal() {
        long[] totals = RunningTotal.runningTotal(new int[] {1, 2, 3, 4});

        assertArrayEquals(new long[] {1, 3, 6, 10}, totals);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        long[] totals = RunningTotal.runningTotal(new int[] {5, -2, -1});

        assertArrayEquals(new long[] {5, 3, 2}, totals);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(RunningTotal::main, "4\n1 2 3 4\n");

        assertEquals("1 3 6 10", output);
    }
}
