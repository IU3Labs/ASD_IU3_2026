package ru.bmstu.lab01.task_33;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountInversionsTest {

    @Test
    public void testStandardArray() {
        //массив {5, 2, 9, 1, 7} имеет 5 инверсий: (5,2), (5,1), (2,1), (9,1), (9,7)
        assertEquals(5L, CountInversions.countInversions(new int[]{5, 2, 9, 1, 7}));
    }

    @Test
    public void testSortedArray() {
        //в отсортированном массиве инверсий нет
        assertEquals(0L, CountInversions.countInversions(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testReverseSortedArray() {
        //в обратном массиве максимальное количество инверсий: n*(n-1)/2
        //для 5 элементов это 10
        assertEquals(10L, CountInversions.countInversions(new int[]{5, 4, 3, 2, 1}));
    }

    @Test
    public void testEmptyArray() {
        assertEquals(0L, CountInversions.countInversions(new int[]{}));
    }
}