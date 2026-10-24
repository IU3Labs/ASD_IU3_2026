package ru.bmstu.lab01.task_10;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LastMultipleTest {
    @Test
    public void testLastMultiple() {
        //кратные 3: 9(индекс 2), 6(индекс 4). Последний - 4
        assertEquals(4, LastMultiple.findLastMultiple(new int[]{5, 2, 9, 1, 6, 7}, 3));
        //ищем кратное 10. В массиве {5, 2, 9, 1, 6, 7} таких нет, поэтому -1
        assertEquals(-1, LastMultiple.findLastMultiple(new int[]{5, 2, 9, 1, 6, 7}, 10));
    }
}