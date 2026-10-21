package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task15BubbleSortTest {
    @Test void shouldSortAndCountSwaps() {
        Task15BubbleSort.Result res = Task15BubbleSort.sortWithCount(new int[]{4, 2, 1, 3});
        assertArrayEquals(new int[]{1, 2, 3, 4}, res.sorted());
        assertEquals(4, res.swaps());
    }
    @Test void shouldExitEarlyIfSorted() {
        Task15BubbleSort.Result res = Task15BubbleSort.sortWithCount(new int[]{1, 2, 3});
        assertEquals(0, res.swaps());
    }
    @Test void shouldHandleTwoElements() {
        Task15BubbleSort.Result res = Task15BubbleSort.sortWithCount(new int[]{5, 2});
        assertArrayEquals(new int[]{2, 5}, res.sorted());
        assertEquals(1, res.swaps());
    }
}