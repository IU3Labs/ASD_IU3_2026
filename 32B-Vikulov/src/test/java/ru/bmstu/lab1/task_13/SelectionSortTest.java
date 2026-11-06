package ru.bmstu.lab1.task_13;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class SelectionSortTest {

    @Test
    void shouldSortArrayWithDuplicatesAndNegatives() {
        int[] actual = {3, -1, 2, 3, 0, -1};

        SelectionSort.sort(actual);

        assertArrayEquals(new int[] {-1, -1, 0, 2, 3, 3}, actual);
    }

    @Test
    void shouldSortReversedArray() {
        int[] actual = {5, 4, 3, 2, 1};

        SelectionSort.sort(actual);

        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, actual);
    }

    @Test
    void shouldMatchReferenceSortOnRandomArray() {
        int[] actual = new Random(13).ints(1_000, -500, 500).toArray();
        int[] expected = actual.clone();
        Arrays.sort(expected);

        SelectionSort.sort(actual);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldPrintSortedArrayFromConsole() {
        assertEquals("1 2 3", ConsoleRunner.run(SelectionSort::main, "3\n3 1 2\n"));
    }
}
