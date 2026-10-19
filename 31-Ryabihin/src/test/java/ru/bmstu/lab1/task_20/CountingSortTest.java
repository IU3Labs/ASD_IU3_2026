package ru.bmstu.lab1.task_20;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class CountingSortTest {

    @Test
    void shouldSortWithNegativesAndDuplicates() {
        int[] actual = CountingSort.sort(new int[] {3, -2, 5, 3, 0, -2});
        assertArrayEquals(new int[] {-2, -2, 0, 3, 3, 5}, actual);
    }

    @Test
    void shouldNotChangeSourceArray() {
        int[] source = {2, 1};
        CountingSort.sort(source);
        assertArrayEquals(new int[] {2, 1}, source);
    }

    @Test
    void shouldReturnEmptyArrayForEmptyInput() {
        assertArrayEquals(new int[0], CountingSort.sort(new int[0]));
    }

    @Test
    void shouldThrowWhenRangeIsTooBig() {
        int[] array = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        assertThrows(IllegalArgumentException.class, () -> CountingSort.sort(array));
    }

    @Test
    void shouldPrintSortedArray() {
        assertEquals("-1 0 2 2", MainRunner.run(Main::run, "4\n2 0 -1 2"));
    }
}
