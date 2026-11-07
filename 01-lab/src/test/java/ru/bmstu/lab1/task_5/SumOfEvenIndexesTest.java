package ru.bmstu.lab1.task_5;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumOfEvenIndexesTest {

    @Test
    void shouldAddEvenPositions() {
        long sum = SumOfEvenIndexes.sumEvenIndexes(new int[] {1, 2, 3, 4, 5});

        assertEquals(9, sum);
    }

    @Test
    void shouldAddOnlyFirstWhenThereAreTwoNumbers() {
        long sum = SumOfEvenIndexes.sumEvenIndexes(new int[] {8, 100});

        assertEquals(8, sum);
    }

    @Test
    void shouldKeepLargeSum() {
        long sum = SumOfEvenIndexes.sumEvenIndexes(new int[] {1500000000, 1, 1500000000});

        assertEquals(3000000000L, sum);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(SumOfEvenIndexes::main, "5\n1 2 3 4 5\n");

        assertEquals("9", output);
    }
}
