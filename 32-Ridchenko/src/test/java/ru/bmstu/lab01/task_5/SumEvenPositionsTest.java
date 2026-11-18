package ru.bmstu.lab01.task_5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SumEvenPositionsTest {

    @Test
    public void testSumEvenPositions() {
        //индексы 0, 2, 4 -> элементы 5, 9, 7. Сумма = 21
        assertEquals(21, SumEvenPositions.sumEvenPositions(new int[]{5, 2, 9, 1, 7}));
    }

    @Test
    public void testEmptyArray() {
        assertEquals(0, SumEvenPositions.sumEvenPositions(new int[]{}));
    }
}