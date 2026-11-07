package ru.bmstu.lab1.task_14;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InsertionSortTest {

    @Test
    void shouldSortDescending() {
        int[] numbers = {1, 3, 2, 2};
        InsertionSort.sortDescending(numbers);

        assertArrayEquals(new int[] {3, 2, 2, 1}, numbers);
    }

    @Test
    void shouldLeaveDescendingArrayAsIs() {
        int[] numbers = {5, 4, 1};
        InsertionSort.sortDescending(numbers);

        assertArrayEquals(new int[] {5, 4, 1}, numbers);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(InsertionSort::main, "4\n1 3 2 2\n");

        assertEquals("3 2 2 1", output);
    }
}
