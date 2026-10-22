package ru.bmstu.lab1.task_39;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

/**
 * Тесты задачи 39 «Циклический сдвиг вправо».
 */
class RightCyclicShiftTest {

    @Test
    void shouldShiftRightWhenShiftIsLessThanLength() {
        int[] array = {1, 2, 3, 4, 5};

        RightCyclicShift.shiftRight(array, 2);

        assertArrayEquals(new int[] {4, 5, 1, 2, 3}, array);
    }

    @Test
    void shouldReduceShiftModuloLengthWhenShiftIsGreaterThanLength() {
        int[] array = {1, 2, 3, 4, 5};

        RightCyclicShift.shiftRight(array, 12);

        assertArrayEquals(new int[] {4, 5, 1, 2, 3}, array);
    }

    @Test
    void shouldKeepArrayWhenShiftIsMultipleOfLength() {
        int[] array = {1, 2, 3};

        RightCyclicShift.shiftRight(array, 3_000_000_000L);

        assertArrayEquals(new int[] {1, 2, 3}, array);
    }

    @Test
    void shouldShiftLeftWhenShiftIsNegative() {
        int[] array = {1, 2, 3, 4, 5};

        RightCyclicShift.shiftRight(array, -1);

        assertArrayEquals(new int[] {2, 3, 4, 5, 1}, array);
    }

    @Test
    void shouldDoNothingForEmptyArray() {
        int[] array = {};

        RightCyclicShift.shiftRight(array, 5);

        assertArrayEquals(new int[] {}, array);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> RightCyclicShift.shiftRight(null, 1));
    }

    @Test
    void shouldPrintShiftedArrayFromConsoleInput() {
        List<String> actual = ConsoleRunner.execute(RightCyclicShift::run, "4\n10 20 30 40\n1");

        assertEquals(List.of("40 10 20 30"), actual);
    }
}
