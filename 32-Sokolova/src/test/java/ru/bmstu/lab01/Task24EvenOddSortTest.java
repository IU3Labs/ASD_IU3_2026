package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task24EvenOddSortTest {
    @Test void shouldSortEvensAscOddsDesc() { assertArrayEquals(new int[]{2, 4, 5, 3, 1}, Task24EvenOddSort.sortEvenOdd(new int[]{5, 2, 1, 4, 3})); }
    @Test void shouldHandleOnlyEvens() { assertArrayEquals(new int[]{2, 4}, Task24EvenOddSort.sortEvenOdd(new int[]{4, 2})); }
    @Test void shouldHandleOnlyOdds() { assertArrayEquals(new int[]{5, 3, 1}, Task24EvenOddSort.sortEvenOdd(new int[]{1, 5, 3})); }
}