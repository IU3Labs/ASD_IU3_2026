package ru.bmstu.lab01.task_58;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Task58Test {
    private final Task58 solver = new Task58();

    @Test
    public void shouldFillSquareMatrixWithSnake() {
        int[][] expected = {{1, 2}, {4, 3}};
        int[][] actual = solver.fillSnake(2, 2);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldFillRectangularMatrixWithSnake() {
        int[][] expected = {{1, 2, 3}, {6, 5, 4}};
        int[][] actual = solver.fillSnake(2, 3);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldFillSingleRowWithSnake() {
        int[][] expected = {{1, 2, 3}};
        int[][] actual = solver.fillSnake(1, 3);
        Assertions.assertArrayEquals(expected, actual);
    }
}