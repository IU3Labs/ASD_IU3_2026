package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task06GreaterThanAvgTest {
    @Test
    void shouldCountAboveAverage() { assertEquals(2, Task06GreaterThanAvg.countGreaterThanAvg(new int[]{2, 4, 6, 8})); }
    @Test
    void shouldReturnZeroIfAllEqual() { assertEquals(0, Task06GreaterThanAvg.countGreaterThanAvg(new int[]{3, 3, 3})); }
    @Test
    void shouldHandleNegatives() { assertEquals(1, Task06GreaterThanAvg.countGreaterThanAvg(new int[]{-5, -2, -8})); }
}