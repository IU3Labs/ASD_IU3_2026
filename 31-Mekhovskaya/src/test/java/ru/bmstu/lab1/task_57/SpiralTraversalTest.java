package ru.bmstu.lab1.task_57;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

/**
 * Тесты задачи 57 «Спиральный обход матрицы».
 */
class SpiralTraversalTest {

    @Test
    void shouldTraverseSquareMatrix() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        assertArrayEquals(new int[] {1, 2, 3, 6, 9, 8, 7, 4, 5}, SpiralTraversal.spiralOrder(matrix));
    }

    @Test
    void shouldTraverseWideRectangularMatrix() {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};

        assertArrayEquals(new int[] {1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7}, SpiralTraversal.spiralOrder(matrix));
    }

    @Test
    void shouldTraverseTallRectangularMatrix() {
        int[][] matrix = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};

        assertArrayEquals(new int[] {1, 2, 4, 6, 8, 7, 5, 3}, SpiralTraversal.spiralOrder(matrix));
    }

    @Test
    void shouldNotRepeatElementsForSingleRowOrColumn() {
        assertArrayEquals(new int[] {1, 2, 3}, SpiralTraversal.spiralOrder(new int[][] {{1, 2, 3}}));
        assertArrayEquals(new int[] {1, 2, 3}, SpiralTraversal.spiralOrder(new int[][] {{1}, {2}, {3}}));
    }

    @Test
    void shouldThrowWhenMatrixIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> SpiralTraversal.spiralOrder(new int[0][0]));
    }

    @Test
    void shouldPrintSpiralFromConsoleInput() {
        List<String> actual = ConsoleRunner.execute(SpiralTraversal::run, "2 2\n1 2\n3 4");

        assertEquals(List.of("1 2 4 3"), actual);
    }
}
