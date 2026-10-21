package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task20CountingSortTest {
    @Test void shouldSortWithNegatives() { assertArrayEquals(new int[]{-5, -2, 0, 3}, Task20CountingSort.sort(new int[]{0, -5, 3, -2})); }
    @Test void shouldHandleAllSame() { assertArrayEquals(new int[]{4, 4, 4}, Task20CountingSort.sort(new int[]{4, 4, 4})); }
    @Test void shouldHandleEmpty() { assertArrayEquals(new int[]{}, Task20CountingSort.sort(new int[]{})); }
}