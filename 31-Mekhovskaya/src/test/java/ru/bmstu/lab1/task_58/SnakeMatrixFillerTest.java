package ru.bmstu.lab1.task_58;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

/**
 * Тесты задачи 58 «Заполнение матрицы змейкой».
 */
class SnakeMatrixFillerTest {

    @Test
    void shouldAlternateDirectionOnEachRow() {
        int[][] expected = {{1, 2, 3}, {6, 5, 4}, {7, 8, 9}};

        assertArrayEquals(expected, SnakeMatrixFiller.fill(3, 3));
    }

    @Test
    void shouldFillRectangularMatrix() {
        int[][] expected = {{1, 2, 3, 4}, {8, 7, 6, 5}};

        assertArrayEquals(expected, SnakeMatrixFiller.fill(2, 4));
    }

    @Test
    void shouldFillSingleColumnTopToBottom() {
        int[][] expected = {{1}, {2}, {3}};

        assertArrayEquals(expected, SnakeMatrixFiller.fill(3, 1));
    }

    @Test
    void shouldThrowWhenSizeIsNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> SnakeMatrixFiller.fill(0, 3));
        assertThrows(IllegalArgumentException.class, () -> SnakeMatrixFiller.fill(3, -1));
    }

    @Test
    void shouldPrintSnakeMatrixFromConsoleInput() {
        List<String> actual = ConsoleRunner.execute(SnakeMatrixFiller::run, "2 3");

        assertEquals(List.of("1 2 3", "6 5 4"), actual);
    }
}
