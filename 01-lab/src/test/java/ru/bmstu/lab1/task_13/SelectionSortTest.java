package ru.bmstu.lab1.task_13;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectionSortTest {

    @Test
    void shouldSortAscending() {
        int[] numbers = {3, 1, 2, -4, 1};
        SelectionSort.sortAscending(numbers);

        assertArrayEquals(new int[] {-4, 1, 1, 2, 3}, numbers);
    }

    @Test
    void shouldLeaveSortedArrayAsIs() {
        int[] numbers = {1, 2, 3};
        SelectionSort.sortAscending(numbers);

        assertArrayEquals(new int[] {1, 2, 3}, numbers);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(SelectionSort::main, "4\n3 1 2 -4\n");

        assertEquals("-4 1 2 3", output);
    }
}
