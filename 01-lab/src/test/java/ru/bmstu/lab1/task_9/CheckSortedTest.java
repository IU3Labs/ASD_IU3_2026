package ru.bmstu.lab1.task_9;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckSortedTest {

    @Test
    void shouldSayYesWhenArrayDoesNotDecrease() {
        assertTrue(CheckSorted.isNonDecreasing(new int[] {1, 2, 5}));
    }

    @Test
    void shouldSayYesWhenNeighboursAreEqual() {
        assertTrue(CheckSorted.isNonDecreasing(new int[] {1, 2, 2, 3}));
    }

    @Test
    void shouldSayNoWhenArrayDecreases() {
        assertFalse(CheckSorted.isNonDecreasing(new int[] {1, 3, 2}));
    }

    @Test
    void shouldReadArrayFromInput() {
        String yes = ProgramOutput.read(CheckSorted::main, "4\n1 2 2 3\n");
        String no = ProgramOutput.read(CheckSorted::main, "3\n1 3 2\n");

        assertEquals("YES", yes);
        assertEquals("NO", no);
    }
}
