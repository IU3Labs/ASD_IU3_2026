package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task19MergeSortedTest {
    @Test void shouldMergeTwoArrays() { assertArrayEquals(new int[]{1, 2, 3, 4, 5}, Task19MergeSorted.merge(new int[]{1, 3}, new int[]{2, 4, 5})); }
    @Test void shouldHandleOneEmpty() { assertArrayEquals(new int[]{1, 2}, Task19MergeSorted.merge(new int[]{1, 2}, new int[]{})); }
    @Test void shouldHandleDuplicates() { assertArrayEquals(new int[]{2, 2, 2}, Task19MergeSorted.merge(new int[]{2}, new int[]{2, 2})); }
}