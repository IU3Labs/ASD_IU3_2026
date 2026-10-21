package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task14InsertionSortTest {
    @Test void shouldSortDescending() { assertArrayEquals(new int[]{9, 5, 3, 1}, Task14InsertionSort.sortDescending(new int[]{3, 9, 1, 5})); }
    @Test void shouldHandleReverseSorted() { assertArrayEquals(new int[]{5, 4, 3}, Task14InsertionSort.sortDescending(new int[]{3, 4, 5})); }
    @Test void shouldHandleDuplicates() { assertArrayEquals(new int[]{7, 7, 7}, Task14InsertionSort.sortDescending(new int[]{7, 7, 7})); }
}