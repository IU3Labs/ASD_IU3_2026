package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task12PairWithSumTest {
    @Test
    void shouldFindPair() { assertArrayEquals(new int[]{0, 3}, Task12PairWithSum.findPairWithSum(new int[]{2, 7, 11, 15}, 17)); }
    @Test
    void shouldReturnMinusOneIfNone() { assertArrayEquals(new int[]{-1, -1}, Task12PairWithSum.findPairWithSum(new int[]{1, 2, 3}, 10)); }
    @Test
    void shouldPickMinIndices() { assertArrayEquals(new int[]{0, 1}, Task12PairWithSum.findPairWithSum(new int[]{3, 3, 3}, 6)); }
}