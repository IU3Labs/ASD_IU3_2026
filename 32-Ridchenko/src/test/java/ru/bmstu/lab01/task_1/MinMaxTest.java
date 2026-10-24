package ru.bmstu.lab01.task_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinMaxTest {

    @Test
    public void testStandardArray() {
        int[] result = MinMax.findMinMax(new int[]{5, 2, 9, 1, 7});
        assertArrayEquals(new int[]{1, 9}, result);
    }

    @Test
    public void testSingleElement() {
        int[] result = MinMax.findMinMax(new int[]{42});
        assertArrayEquals(new int[]{42, 42}, result);
    }

    @Test
    public void testNegativeNumbers() {
        int[] result = MinMax.findMinMax(new int[]{-10, -5, -20, -1});
        assertArrayEquals(new int[]{-20, -1}, result);
    }

    @Test
    public void testEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> MinMax.findMinMax(new int[]{}));
    }
}