package ru.bmstu.lab01.task_12;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PairWithSumTest {
    @Test
    public void testPairExists() {
        //1 + 4 = 5, индексы 0 и 3
        assertArrayEquals(new int[]{0, 3}, PairWithSum.findPair(new int[]{1, 2, 3, 4, 5}, 5));
    }

    @Test
    public void testPairNotFound() {
        assertArrayEquals(new int[]{-1, -1}, PairWithSum.findPair(new int[]{1, 2, 3, 4, 5}, 10));
    }
}