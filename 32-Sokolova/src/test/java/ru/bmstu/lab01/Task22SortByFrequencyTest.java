package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task22SortByFrequencyTest {
    @Test void shouldSortByFreqDesc() { assertArrayEquals(new int[]{2, 2, 3, 3, 1}, Task22SortByFrequency.sortByFreq(new int[]{1, 2, 3, 2, 3})); }
    @Test void shouldHandleEqualFreqByValueAsc() { assertArrayEquals(new int[]{1, 1, 2, 2}, Task22SortByFrequency.sortByFreq(new int[]{2, 1, 2, 1})); }
    @Test void shouldHandleSingleElements() { assertArrayEquals(new int[]{5}, Task22SortByFrequency.sortByFreq(new int[]{5})); }
}