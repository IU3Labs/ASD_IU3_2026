package ru.bmstu.lab01.task_11;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestToAverageTest {
    @Test
    public void testClosest() {
        //сумма 30, среднее 6.0. Ближайшие 5 и 7 (разница 1). Берем первый -> 5
        assertEquals(5, ClosestToAverage.findClosest(new int[]{5, 2, 9, 1, 7, 6}));
    }
}