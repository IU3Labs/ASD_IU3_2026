package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task04AllIndicesTest {
    @Test
    void shouldReturnAllIndices() { assertArrayEquals(new int[]{1, 3, 4}, Task04AllIndices.findAllIndices(new int[]{5, 2, 5, 2, 2}, 2)); }
    @Test
    void shouldReturnEmptyIfNone() { assertArrayEquals(new int[]{}, Task04AllIndices.findAllIndices(new int[]{1, 2}, 3)); }
    @Test
    void shouldReturnSingleIndex() { assertArrayEquals(new int[]{0}, Task04AllIndices.findAllIndices(new int[]{9, 1, 2}, 9)); }
}