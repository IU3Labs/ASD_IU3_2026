package ru.bmstu.lab01.task_60;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.common.ConsoleRunner;

class IslandCounterTest {

    private static final int LARGE_SIZE = 1000;

    @Test
    void shouldCountSeparateIslands() {
        int[][] grid = {
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 1},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1},
        };

        assertEquals(5, IslandCounter.count(grid));
    }

    @Test
    void shouldNotConnectCellsDiagonally() {
        int[][] grid = {
            {1, 0, 1},
            {0, 1, 0},
            {1, 0, 1},
        };

        assertEquals(5, IslandCounter.count(grid));
    }

    @Test
    void shouldCountWindingIslandAsOne() {
        int[][] grid = {
            {1, 1, 1},
            {0, 0, 1},
            {1, 1, 1},
            {1, 0, 0},
        };

        assertEquals(1, IslandCounter.count(grid));
    }

    @Test
    void shouldReturnZeroForWaterOnly() {
        assertEquals(0, IslandCounter.count(new int[3][4]));
    }

    @Test
    void shouldHandleLargeIslandWithoutStackOverflow() {
        int[][] grid = new int[LARGE_SIZE][LARGE_SIZE];
        for (int[] row : grid) {
            Arrays.fill(row, 1);
        }

        assertEquals(1, IslandCounter.count(grid));
    }

    @Test
    void shouldNotModifySourceGrid() {
        int[][] grid = {{1, 0}, {0, 1}};

        IslandCounter.count(grid);

        assertArrayEquals(new int[][] {{1, 0}, {0, 1}}, grid);
    }

    @Test
    void shouldRejectNonBinaryValues() {
        int[][] grid = {{1, 2}};

        assertThrows(IllegalArgumentException.class, () -> IslandCounter.count(grid));
    }

    @Test
    void shouldReadGridAndPrintIslandCount() {
        assertEquals("2", ConsoleRunner.run(IslandCounter::run, "2 3\n1 0 1\n1 0 1\n"));
    }
}
