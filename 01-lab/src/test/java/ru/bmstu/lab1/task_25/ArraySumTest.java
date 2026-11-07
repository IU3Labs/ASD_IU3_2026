package ru.bmstu.lab1.task_25;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArraySumTest {

    @Test
    void shouldAddAllNumbers() {
        long sum = ArraySum.sumAll(new int[] {1, 2, 3, 4});

        assertEquals(10, sum);
    }

    @Test
    void shouldAddNegativeNumbers() {
        long sum = ArraySum.sumAll(new int[] {-5, 2, -1});

        assertEquals(-4, sum);
    }

    @Test
    void shouldKeepLargeSum() {
        long sum = ArraySum.sumAll(new int[] {1500000000, 1500000000});

        assertEquals(3000000000L, sum);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(ArraySum::main, "4\n1 2 3 4\n");

        assertEquals("10", output);
    }
}
