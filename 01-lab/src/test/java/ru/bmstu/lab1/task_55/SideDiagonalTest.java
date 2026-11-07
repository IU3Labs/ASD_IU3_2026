package ru.bmstu.lab1.task_55;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SideDiagonalTest {

    @Test
    void shouldAddSideDiagonal() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        assertEquals(15, SideDiagonal.sumSideDiagonal(matrix));
    }

    @Test
    void shouldAddNegativeNumbers() {
        int[][] matrix = {
                {1, -2},
                {3, 4}
        };

        assertEquals(1, SideDiagonal.sumSideDiagonal(matrix));
    }

    @Test
    void shouldReadMatrixFromInput() {
        String output = ProgramOutput.read(SideDiagonal::main, "3\n1 2 3\n4 5 6\n7 8 9\n");

        assertEquals("15", output);
    }
}
