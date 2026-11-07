package ru.bmstu.lab1.task_21;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthSmallestTest {

    @Test
    void shouldFindKthSmallest() {
        int value = KthSmallest.findKthSmallest(new int[] {4, 1, 3, 2}, 2);

        assertEquals(2, value);
    }

    @Test
    void shouldFindSmallestAndLargest() {
        int smallest = KthSmallest.findKthSmallest(new int[] {4, 1, 3}, 1);
        int largest = KthSmallest.findKthSmallest(new int[] {4, 1, 3}, 3);

        assertEquals(1, smallest);
        assertEquals(4, largest);
    }

    @Test
    void shouldTreatEqualNumbersAsSeparate() {
        int value = KthSmallest.findKthSmallest(new int[] {5, 5, 1}, 2);

        assertEquals(5, value);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(KthSmallest::main, "4 2\n4 1 3 2\n");

        assertEquals("2", output);
    }
}
