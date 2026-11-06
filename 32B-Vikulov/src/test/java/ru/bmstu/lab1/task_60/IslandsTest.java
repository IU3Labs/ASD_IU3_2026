package ru.bmstu.lab1.task_60;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class IslandsTest {

    @Test
    void shouldCountSeparateIslands() {
        int[][] grid = {
            {1, 1, 0, 0},
            {1, 0, 0, 1},
            {0, 0, 1, 1},
            {1, 0, 0, 0},
        };

        assertEquals(3, Islands.count(grid));
    }

    @Test
    void shouldNotConnectDiagonalCells() {
        int[][] grid = {
            {1, 0, 1},
            {0, 1, 0},
            {1, 0, 1},
        };

        assertEquals(5, Islands.count(grid));
    }

    @Test
    void shouldFollowWindingIsland() {
        int[][] grid = {
            {1, 1, 1},
            {0, 0, 1},
            {1, 1, 1},
        };

        assertEquals(1, Islands.count(grid));
    }

    @Test
    void shouldReturnZeroForWaterOrEmptyGrid() {
        assertEquals(0, Islands.count(new int[][] {{0, 0}, {0, 0}}));
        assertEquals(0, Islands.count(new int[0][0]));
    }

    @Test
    void shouldHandleLargeSolidGridWithoutStackOverflow() {
        int[][] grid = new int[1_000][1_000];
        for (int[] row : grid) {
            Arrays.fill(row, 1);
        }

        assertEquals(1, Islands.count(grid));
    }

    @Test
    void shouldNotModifyInputGrid() {
        int[][] grid = {{1, 1}, {0, 1}};

        Islands.count(grid);

        assertArrayEquals(new int[] {1, 1}, grid[0]);
        assertArrayEquals(new int[] {0, 1}, grid[1]);
    }

    @Test
    void shouldPrintCountFromConsole() {
        assertEquals("2", ConsoleRunner.run(Islands::main, "2 3\n1 0 1\n1 0 1\n"));
    }
}
