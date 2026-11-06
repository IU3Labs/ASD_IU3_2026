package ru.bmstu.lab1.task_38;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class RotateLeftTest {

    private static int[] rotated(long shift) {
        int[] values = {1, 2, 3, 4, 5};
        RotateLeft.rotate(values, shift);
        return values;
    }

    @Test
    void shouldRotateByTwo() {
        assertArrayEquals(new int[] {3, 4, 5, 1, 2}, rotated(2));
    }

    @Test
    void shouldNotChangeArrayWhenShiftIsZeroOrMultipleOfLength() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, rotated(0));
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, rotated(10));
    }

    @Test
    void shouldReduceShiftGreaterThanLength() {
        assertArrayEquals(rotated(2), rotated(7));
    }

    @Test
    void shouldTreatNegativeShiftAsRightRotation() {
        assertArrayEquals(new int[] {5, 1, 2, 3, 4}, rotated(-1));
    }

    @Test
    void shouldIgnoreEmptyArray() {
        int[] values = new int[0];

        RotateLeft.rotate(values, 3);

        assertEquals(0, values.length);
    }

    @Test
    void shouldPrintRotatedArrayFromConsole() {
        assertEquals("2 3 1", ConsoleRunner.run(RotateLeft::main, "3\n1 2 3\n1\n"));
    }
}
