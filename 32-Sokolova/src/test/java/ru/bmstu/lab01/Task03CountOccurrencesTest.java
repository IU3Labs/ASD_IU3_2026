package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task03CountOccurrencesTest {
    @Test
    void shouldCountMultipleOccurrences() { assertEquals(3, Task03CountOccurrences.countOccurrences(new int[]{2, 2, 1, 2}, 2)); }
    @Test
    void shouldReturnZeroIfNone() { assertEquals(0, Task03CountOccurrences.countOccurrences(new int[]{1, 2, 3}, 5)); }
    @Test
    void shouldHandleSingleMatch() { assertEquals(1, Task03CountOccurrences.countOccurrences(new int[]{7}, 7)); }
}