package ru.bmstu.lab1.task_15;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BubbleSortTest {

    @Test
    void shouldSortAndCountSwaps() {
        int[] numbers = {3, 2, 1};
        int swaps = BubbleSort.sortAndCountSwaps(numbers);

        assertArrayEquals(new int[] {1, 2, 3}, numbers);
        assertEquals(3, swaps);
    }

    @Test
    void shouldDoZeroSwapsWhenArrayIsSorted() {
        int[] numbers = {1, 2, 3};
        int swaps = BubbleSort.sortAndCountSwaps(numbers);

        assertArrayEquals(new int[] {1, 2, 3}, numbers);
        assertEquals(0, swaps);
    }

    @Test
    void shouldCountOneSwap() {
        int[] numbers = {2, 1, 3};
        int swaps = BubbleSort.sortAndCountSwaps(numbers);

        assertArrayEquals(new int[] {1, 2, 3}, numbers);
        assertEquals(1, swaps);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(BubbleSort::main, "3\n3 2 1\n");

        assertEquals("1 2 3\n3", output);
    }
}
