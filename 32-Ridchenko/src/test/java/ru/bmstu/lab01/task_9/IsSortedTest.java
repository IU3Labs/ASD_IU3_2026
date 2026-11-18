package ru.bmstu.lab01.task_9;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IsSortedTest {
    @Test
    public void testSorted() {
        assertEquals("YES", IsSorted.check(new int[]{1, 2, 2, 3, 5}));
        assertEquals("NO", IsSorted.check(new int[]{1, 3, 2, 4, 5}));
    }
}