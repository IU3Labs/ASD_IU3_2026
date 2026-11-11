package ru.bmstu.lab01.task_18;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Task18Test {
    private final Task18 solver = new Task18();

    @Test
    public void shouldSortMixedNumbers() {
        int[] actual = {4, 1, 8, 3, 2};
        int[] expected = {8, 1, 4, 3, 2};
        solver.sortEvenDescending(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSortOnlyEvenNumbers() {
        int[] actual = {2, 4, 6, 8};
        int[] expected = {8, 6, 4, 2};
        solver.sortEvenDescending(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldLeaveArrayUnchangedWhenOnlyOdd() {
        int[] actual = {1, 3, 5, 7};
        int[] expected = {1, 3, 5, 7};
        solver.sortEvenDescending(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldHandleEmptyArray() {
        int[] actual = {};
        int[] expected = {};
        solver.sortEvenDescending(actual);
        Assertions.assertArrayEquals(expected, actual);
    }
}