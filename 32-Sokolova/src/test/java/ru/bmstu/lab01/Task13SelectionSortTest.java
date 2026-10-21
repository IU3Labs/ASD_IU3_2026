package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task13SelectionSortTest {
    @Test void shouldSortMixedArray() { assertArrayEquals(new int[]{1, 3, 5, 7}, Task13SelectionSort.sort(new int[]{5, 1, 7, 3})); }
    @Test void shouldHandleAlreadySorted() { assertArrayEquals(new int[]{1, 2, 3}, Task13SelectionSort.sort(new int[]{1, 2, 3})); }
    @Test void shouldHandleSingleElement() { assertArrayEquals(new int[]{42}, Task13SelectionSort.sort(new int[]{42})); }
}