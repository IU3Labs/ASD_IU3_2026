package ru.bmstu.lab1.task_49;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RowSumsTest {

    @Test
    void shouldAddEachRow() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };
        long[] sums = RowSums.sumRows(matrix);

        assertArrayEquals(new long[] {6, 15}, sums);
    }

    @Test
    void shouldAddNegativeNumbers() {
        int[][] matrix = {
                {-1, 4},
                {2, -5}
        };
        long[] sums = RowSums.sumRows(matrix);

        assertArrayEquals(new long[] {3, -3}, sums);
    }

    @Test
    void shouldReadMatrixFromInput() {
        String output = ProgramOutput.read(RowSums::main, "2 3\n1 2 3\n4 5 6\n");

        assertEquals("6 15", output);
    }
}
