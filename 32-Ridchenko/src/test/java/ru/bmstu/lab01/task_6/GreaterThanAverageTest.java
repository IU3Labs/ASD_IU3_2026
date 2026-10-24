package ru.bmstu.lab01.task_6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GreaterThanAverageTest {
    @Test
    public void testCount() {
        //сумма 30, среднее 5. Больше 5: 9, 7, 6. Ответ 3
        assertEquals(3, GreaterThanAverage.countGreaterThanAverage(new int[]{5, 2, 9, 1, 7, 6}));
    }
}