package ru.bmstu.lab1.task_7;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecondLargestTest {

    @Test
    void shouldFindSecondLargest() {
        int second = SecondLargest.findSecondLargest(new int[] {1, 3, 5});

        assertEquals(3, second);
    }

    @Test
    void shouldFindSecondWhenLargestIsFirst() {
        int second = SecondLargest.findSecondLargest(new int[] {5, 1, 3});

        assertEquals(3, second);
    }

    @Test
    void shouldFindSecondWhenLargestIsLast() {
        int second = SecondLargest.findSecondLargest(new int[] {1, 5, 3});

        assertEquals(3, second);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int second = SecondLargest.findSecondLargest(new int[] {-10, -1, -4});

        assertEquals(-4, second);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(SecondLargest::main, "3\n1 5 3\n");

        assertEquals("3", output);
    }
}
