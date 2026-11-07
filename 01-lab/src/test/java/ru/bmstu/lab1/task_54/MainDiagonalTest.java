package ru.bmstu.lab1.task_54;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainDiagonalTest {

    @Test
    void shouldAddMainDiagonal() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        assertEquals(15, MainDiagonal.sumMainDiagonal(matrix));
    }

    @Test
    void shouldAddNegativeNumbers() {
        int[][] matrix = {
                {-2, 5},
                {8, 4}
        };

        assertEquals(2, MainDiagonal.sumMainDiagonal(matrix));
    }

    @Test
    void shouldReadMatrixFromInput() {
        String output = ProgramOutput.read(MainDiagonal::main, "2\n1 2\n3 4\n");

        assertEquals("5", output);
    }
}
