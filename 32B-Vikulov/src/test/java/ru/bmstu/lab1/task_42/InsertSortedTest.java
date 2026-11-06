package ru.bmstu.lab1.task_42;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class InsertSortedTest {

    private static final int[] SORTED = {1, 3, 3, 7};

    @Test
    void shouldInsertIntoMiddle() {
        assertArrayEquals(new int[] {1, 3, 3, 5, 7}, InsertSorted.insert(SORTED, 5));
    }

    @Test
    void shouldInsertAtBeginningAndEnd() {
        assertArrayEquals(new int[] {0, 1, 3, 3, 7}, InsertSorted.insert(SORTED, 0));
        assertArrayEquals(new int[] {1, 3, 3, 7, 9}, InsertSorted.insert(SORTED, 9));
    }

    @Test
    void shouldPlaceEqualValueAfterExistingOnes() {
        assertEquals(3, InsertSorted.upperBound(SORTED, 3));
    }

    @Test
    void shouldInsertIntoEmptyArray() {
        assertArrayEquals(new int[] {4}, InsertSorted.insert(new int[0], 4));
    }

    @Test
    void shouldPrintArrayFromConsole() {
        assertEquals("1 2 3", ConsoleRunner.run(InsertSorted::main, "2\n1 3\n2\n"));
    }
}
