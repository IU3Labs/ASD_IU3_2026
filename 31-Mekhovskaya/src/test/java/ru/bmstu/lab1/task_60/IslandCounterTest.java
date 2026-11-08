package ru.bmstu.lab1.task_60;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

/**
 * Тесты задачи 60 «Острова из единиц».
 */
class IslandCounterTest {

    /** Размер стороны матрицы для стресс-теста: 1000 x 1000 = 10^6 клеток. */
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

        assertEquals(5, IslandCounter.countIslands(grid));
    }

    @Test
    void shouldNotConnectCellsByDiagonal() {
        int[][] grid = {{1, 0}, {0, 1}};

        assertEquals(2, IslandCounter.countIslands(grid));
    }

    @Test
    void shouldCountWindingIslandAsOne() {
        // Остров в форме буквы U: при обходе приходится возвращаться назад.
        int[][] grid = {{1, 0, 1}, {1, 0, 1}, {1, 1, 1}};

        assertEquals(1, IslandCounter.countIslands(grid));
    }

    @Test
    void shouldReturnZeroWhenNoLand() {
        assertEquals(0, IslandCounter.countIslands(new int[][] {{0, 0}, {0, 0}}));
    }

    @Test
    void shouldNotModifyInputGrid() {
        int[][] grid = {{1, 1}, {0, 1}};

        IslandCounter.countIslands(grid);

        assertArrayEquals(new int[][] {{1, 1}, {0, 1}}, grid);
    }

    @Test
    void shouldHandleLargeGridWithoutStackOverflow() {
        int[][] grid = new int[LARGE_SIZE][LARGE_SIZE];
        for (int[] row : grid) {
            Arrays.fill(row, 1);
        }

        assertEquals(1, IslandCounter.countIslands(grid));
    }

    @Test
    void shouldThrowWhenGridContainsNonBinaryValue() {
        assertThrows(IllegalArgumentException.class, () -> IslandCounter.countIslands(new int[][] {{1, 2}}));
    }

    @Test
    void shouldPrintIslandCountFromConsoleInput() {
        List<String> actual = ConsoleRunner.execute(IslandCounter::run, "3 3\n1 0 1\n0 0 0\n1 0 1");

        assertEquals(List.of("4"), actual);
    }
}
