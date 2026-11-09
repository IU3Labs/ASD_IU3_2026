package ru.bmstu.lab1.task_38;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class RotateLeftTest {

    @Test
    void shouldRotateLeft() {
        int[] array = {1, 2, 3, 4, 5};
        RotateLeft.rotate(array, 2);
        assertArrayEquals(new int[] {3, 4, 5, 1, 2}, array);
    }

    @Test
    void shouldNotChangeArrayWhenShiftIsLength() {
        int[] array = {1, 2, 3};
        RotateLeft.rotate(array, 3);
        assertArrayEquals(new int[] {1, 2, 3}, array);
    }

    @Test
    void shouldWorkWhenShiftIsBiggerThanLength() {
        int[] array = {1, 2, 3, 4};
        RotateLeft.rotate(array, 5);
        assertArrayEquals(new int[] {2, 3, 4, 1}, array);
    }

    @Test
    void shouldRotateRightWhenShiftIsNegative() {
        int[] array = {1, 2, 3, 4};
        RotateLeft.rotate(array, -1);
        assertArrayEquals(new int[] {4, 1, 2, 3}, array);
    }

    @Test
    void shouldDoNothingWithEmptyArray() {
        int[] array = {};
        RotateLeft.rotate(array, 2);
        assertArrayEquals(new int[0], array);
    }

    @Test
    void shouldPrintRotatedArray() {
        assertEquals("3 4 5 1 2", MainRunner.run(Main::run, "5\n1 2 3 4 5\n2"));
    }
}
