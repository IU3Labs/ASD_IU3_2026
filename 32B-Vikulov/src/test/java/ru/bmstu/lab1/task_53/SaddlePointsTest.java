package ru.bmstu.lab1.task_53;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;
import ru.bmstu.lab1.task_53.SaddlePoints.Point;

class SaddlePointsTest {

    @Test
    void shouldFindSingleSaddlePoint() {
        int[][] matrix = {
            {3, 8, 7},
            {5, 6, 9},
            {1, 4, 2},
        };

        assertEquals(List.of(new Point(1, 0, 5)), SaddlePoints.find(matrix));
    }

    @Test
    void shouldReturnEmptyListWhenNoSaddlePoints() {
        int[][] matrix = {
            {1, 2},
            {2, 1},
        };

        assertTrue(SaddlePoints.find(matrix).isEmpty());
    }

    @Test
    void shouldReturnEveryCellOfConstantMatrix() {
        assertEquals(4, SaddlePoints.find(new int[][] {{7, 7}, {7, 7}}).size());
    }

    @Test
    void shouldReturnEmptyListForEmptyMatrix() {
        assertTrue(SaddlePoints.find(new int[0][0]).isEmpty());
    }

    @Test
    void shouldPrintPointsFromConsole() {
        String input = "2 3\n1 2 3\n4 5 6\n";

        assertEquals("(1, 0, 4)", ConsoleRunner.run(SaddlePoints::main, input));
    }
}
