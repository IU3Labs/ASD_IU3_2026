package ru.bmstu.lab1.task_1;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinAndMaxTest {

    @Test
    void shouldFindMinAndMax() {
        int[] answer = MinAndMax.findMinAndMax(new int[] {4, 1, 7, 2});

        assertEquals(1, answer[0]);
        assertEquals(7, answer[1]);
    }

    @Test
    void shouldWorkWhenNumbersAreNegative() {
        int[] answer = MinAndMax.findMinAndMax(new int[] {-2, -8, -1});

        assertEquals(-8, answer[0]);
        assertEquals(-1, answer[1]);
    }

    @Test
    void shouldWorkWhenAllNumbersAreEqual() {
        int[] answer = MinAndMax.findMinAndMax(new int[] {5, 5});

        assertEquals(5, answer[0]);
        assertEquals(5, answer[1]);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(MinAndMax::main, "4\n4 1 7 2\n");

        assertEquals("1 7", output);
    }
}
