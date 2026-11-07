package ru.bmstu.lab1.task_50;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnSumsTest {

    @Test
    void shouldAddEachColumn() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };
        long[] sums = ColumnSums.sumColumns(matrix);

        assertArrayEquals(new long[] {5, 7, 9}, sums);
    }

    @Test
    void shouldAddNegativeNumbers() {
        int[][] matrix = {
                {-1, 4},
                {2, -5}
        };
        long[] sums = ColumnSums.sumColumns(matrix);

        assertArrayEquals(new long[] {1, -1}, sums);
    }

    @Test
    void shouldReadMatrixFromInput() {
        String output = ProgramOutput.read(ColumnSums::main, "2 3\n1 2 3\n4 5 6\n");

        assertEquals("5 7 9", output);
    }
}
