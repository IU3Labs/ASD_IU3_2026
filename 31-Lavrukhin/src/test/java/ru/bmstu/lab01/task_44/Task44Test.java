package ru.bmstu.lab01.task_44;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.ConsoleHelper;

class Task44Test {

    @Test
    void shouldAlternateSignsAndKeepOrder() {
        int[] array = {-1, 2, -3, -4, 5, 6};

        assertArrayEquals(new int[] {2, -1, 5, -3, 6, -4}, Task44.alternate(array));
    }

    @Test
    void shouldWorkWhenAllPositivesGoFirst() {
        assertArrayEquals(new int[] {1, -1, 2, -2}, Task44.alternate(new int[] {1, 2, -1, -2}));
    }

    @Test
    void shouldKeepAlreadyAlternatingArray() {
        assertArrayEquals(new int[] {3, -3, 4, -4}, Task44.alternate(new int[] {3, -3, 4, -4}));
    }

    @Test
    void shouldReturnEmptyArrayForEmptyInput() {
        assertEquals(0, Task44.alternate(new int[0]).length);
    }

    @Test
    void shouldThrowWhenThereIsZero() {
        assertThrows(IllegalArgumentException.class, () -> Task44.alternate(new int[] {1, 0}));
    }

    @Test
    void shouldThrowWhenTooManyPositives() {
        assertThrows(IllegalArgumentException.class, () -> Task44.alternate(new int[] {1, 2, 3, -1}));
    }

    @Test
    void shouldThrowWhenTooManyNegatives() {
        assertThrows(IllegalArgumentException.class, () -> Task44.alternate(new int[] {-1, -2, 1, -3}));
    }

    @Test
    void shouldThrowWhenLengthIsOdd() {
        assertThrows(IllegalArgumentException.class, () -> Task44.alternate(new int[] {1, -1, 2}));
    }

    @Test
    void shouldReadInputAndPrintAlternatedArray() {
        String output = ConsoleHelper.run(() -> Task44.main(new String[0]), "4\n-7 -8 9 10\n");

        assertEquals("9 -7 10 -8", output);
    }
}
