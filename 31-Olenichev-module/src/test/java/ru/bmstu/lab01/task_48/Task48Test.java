package ru.bmstu.lab01.task_48;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Task48Test {
    private final Task48 solver = new Task48();

    @Test
    public void shouldTranspose2x2Matrix() {
        int[][] actual = {{1, 2}, {3, 4}};
        int[][] expected = {{1, 3}, {2, 4}};
        solver.transposeInPlace(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldTranspose3x3Matrix() {
        int[][] actual = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] expected = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        solver.transposeInPlace(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldHandle1x1Matrix() {
        int[][] actual = {{42}};
        int[][] expected = {{42}};
        solver.transposeInPlace(actual);
        Assertions.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void shouldHandleEmptyMatrix() {
        int[][] actual = {};
        int[][] expected = {};
        solver.transposeInPlace(actual);
        Assertions.assertArrayEquals(expected, actual);
    }
}