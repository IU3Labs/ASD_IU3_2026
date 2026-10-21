package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task05SumEvenIndicesTest {
    @Test
    void shouldSumEvenPositions() { assertEquals(13L, Task05SumEvenIndices.sumEvenIndices(new int[]{1, 2, 3, 4, 9})); }
    @Test
    void shouldHandleSingleElement() { assertEquals(5L, Task05SumEvenIndices.sumEvenIndices(new int[]{5})); }
    @Test
    void shouldReturnZeroForEmpty() { assertEquals(0L, Task05SumEvenIndices.sumEvenIndices(new int[]{})); }
}