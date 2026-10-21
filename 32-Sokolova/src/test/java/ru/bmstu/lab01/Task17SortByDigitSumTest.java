package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task17SortByDigitSumTest {
    @Test void shouldSortByDigitSumStable() { assertArrayEquals(new int[]{10, 20, 12, 3}, Task17SortByDigitSum.sortByDigitSum(new int[]{12, 3, 10, 20})); }
    @Test void shouldHandleSameSums() { assertArrayEquals(new int[]{28, 91, 19}, Task17SortByDigitSum.sortByDigitSum(new int[]{28, 91, 19})); }
    @Test void shouldHandleZeros() { assertArrayEquals(new int[]{0, 10, 20}, Task17SortByDigitSum.sortByDigitSum(new int[]{20, 0, 10})); }
}