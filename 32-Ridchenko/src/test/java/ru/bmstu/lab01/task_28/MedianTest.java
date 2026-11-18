package ru.bmstu.lab01.task_28;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MedianTest {

    @Test
    public void testOddLength() {
        //отсортированный массив: 1, 2, 5, 7, 9. Центральный - 5
        assertEquals(5.0, Median.findMedian(new int[]{5, 2, 9, 1, 7}));
    }

    @Test
    public void testEvenLength() {
        //отсортированный массив: 1, 2, 5, 6. Центральные 2 и 5. Среднее (2+5)/2 = 3.5
        assertEquals(3.5, Median.findMedian(new int[]{5, 2, 6, 1}));
    }

    @Test
    public void testSingleElement() {
        assertEquals(42.0, Median.findMedian(new int[]{42}));
    }

    @Test
    public void testEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Median.findMedian(new int[]{}));
    }
}