package ru.bmstu.lab01.task_8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FilterBySignTest {
    @Test
    public void testFilter() {
        int[][] result = FilterBySign.filter(new int[]{-1, 2, 0, -3, 4});
        assertArrayEquals(new int[]{2, 4}, result[0]); //положительные
        assertArrayEquals(new int[]{-1, -3}, result[1]); //отрицательные
    }
}