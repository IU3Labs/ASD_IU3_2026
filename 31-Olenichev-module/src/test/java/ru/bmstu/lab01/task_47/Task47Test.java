package ru.bmstu.lab01.task_47;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Task47Test {
    private final Task47 solver = new Task47();

    @Test
    public void shouldRotateSquareMatrix() {
        int[][] actual = {{1, 2}, {3, 4}};
        int[][] expected = {{3, 1}, {4, 2}};
        Assertions.assertArrayEquals(expected, solver.rotate90(actual));
    }

    @Test
    public void shouldRotateRectangularMatrix() {
        int[][] actual = {{1, 2, 3}, {4, 5, 6}};
        int[][] expected = {{4, 1}, {5, 2}, {6, 3}};
        Assertions.assertArrayEquals(expected, solver.rotate90(actual));
    }

    @Test
    public void shouldRotateSingleRow() {
        int[][] actual = {{1, 2, 3}};
        int[][] expected = {{1}, {2}, {3}};
        Assertions.assertArrayEquals(expected, solver.rotate90(actual));
    }

    @Test
    public void shouldHandleEmptyMatrix() {
        int[][] actual = {};
        int[][] expected = {};
        Assertions.assertArrayEquals(expected, solver.rotate90(actual));
    }
}