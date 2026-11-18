package ru.bmstu.lab01.task_7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SecondMaxTest {
    @Test
    public void testSecondMax() {
        assertEquals(7, SecondMax.findSecondMax(new int[]{5, 2, 9, 1, 7}));
    }
}