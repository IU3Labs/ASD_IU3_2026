package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task18SortEvensInPlaceTest {
    @Test void shouldSortEvensKeepOdds() { assertArrayEquals(new int[]{5, 2, 3, 4, 1}, Task18SortEvensInPlace.sortEvensOnly(new int[]{5, 4, 3, 2, 1})); }
    @Test void shouldHandleNoEvens() { assertArrayEquals(new int[]{1, 3, 5}, Task18SortEvensInPlace.sortEvensOnly(new int[]{1, 3, 5})); }
    @Test void shouldHandleAllEvens() { assertArrayEquals(new int[]{2, 4, 6}, Task18SortEvensInPlace.sortEvensOnly(new int[]{6, 2, 4})); }
}