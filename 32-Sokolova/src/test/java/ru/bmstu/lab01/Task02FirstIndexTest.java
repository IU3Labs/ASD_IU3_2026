package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task02FirstIndexTest {
    @Test
    void shouldReturnFirstIndexIfExists() { assertEquals(1, Task02FirstIndex.findFirstIndex(new int[]{5, 3, 3, 7}, 3)); }
    @Test
    void shouldReturnMinusOneIfNotFound() { assertEquals(-1, Task02FirstIndex.findFirstIndex(new int[]{1, 2, 3}, 4)); }
    @Test
    void shouldReturnZeroIfAtStart() { assertEquals(0, Task02FirstIndex.findFirstIndex(new int[]{9, 9, 9}, 9)); }
}