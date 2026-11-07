package ru.bmstu.lab1.task_27;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumOfSquaresTest {

    @Test
    void shouldAddSquares() {
        long sum = SumOfSquares.sumOfSquares(new int[] {1, -2, 3});

        assertEquals(14, sum);
    }

    @Test
    void shouldSquareLargeNumbers() {
        long sum = SumOfSquares.sumOfSquares(new int[] {100000, 100000});

        assertEquals(20000000000L, sum);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(SumOfSquares::main, "3\n1 -2 3\n");

        assertEquals("14", output);
    }
}
