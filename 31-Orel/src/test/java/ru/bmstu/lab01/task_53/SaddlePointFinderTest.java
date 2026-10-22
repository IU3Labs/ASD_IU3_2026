package ru.bmstu.lab01.task_53;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.common.ConsoleRunner;

class SaddlePointFinderTest {

    @Test
    void shouldFindSingleSaddlePoint() {
        int[][] matrix = {
            {5, 6, 7},
            {1, 2, 3},
            {0, 4, 8},
        };
        // 5 — минимум строки 0 и максимум столбца 0
        List<SaddlePoint> expected = List.of(new SaddlePoint(0, 0, 5));

        assertEquals(expected, SaddlePointFinder.find(matrix));
    }

    @Test
    void shouldFindAllSaddlePointsWithEqualValues() {
        int[][] matrix = {
            {5, 5},
            {5, 5},
        };

        assertEquals(4, SaddlePointFinder.find(matrix).size());
    }

    @Test
    void shouldReturnEmptyListWhenNoSaddlePoint() {
        int[][] matrix = {
            {1, 2},
            {2, 1},
        };

        assertTrue(SaddlePointFinder.find(matrix).isEmpty());
    }

    @Test
    void shouldReturnEmptyListForMatrixWithoutColumns() {
        assertTrue(SaddlePointFinder.find(new int[2][0]).isEmpty());
    }

    @Test
    void shouldFormatPointAsInTaskStatement() {
        assertEquals("(1, 2, -7)", new SaddlePoint(1, 2, -7).toString());
    }

    @Test
    void shouldPrintEachSaddlePointOnSeparateLine() {
        String actual = ConsoleRunner.run(SaddlePointFinder::run, "2 2\n4 4\n1 2\n");

        assertEquals("(0, 0, 4)\n(0, 1, 4)", actual);
    }

    @Test
    void shouldPrintMinusOneWhenNoSaddlePoint() {
        assertEquals("-1", ConsoleRunner.run(SaddlePointFinder::run, "2 2\n1 2\n2 1\n"));
    }
}
