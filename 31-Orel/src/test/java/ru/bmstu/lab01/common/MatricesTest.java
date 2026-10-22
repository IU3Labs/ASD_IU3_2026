package ru.bmstu.lab01.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MatricesTest {

    @Test
    void shouldReturnColumnCountForRectangularMatrix() {
        assertEquals(3, Matrices.requireRectangular(new int[][] {{1, 2, 3}, {4, 5, 6}}));
    }

    @Test
    void shouldReturnZeroForMatrixWithoutRows() {
        assertEquals(0, Matrices.requireRectangular(new int[0][]));
    }

    @Test
    void shouldRejectJaggedMatrix() {
        int[][] jagged = {{1, 2}, {3}};

        assertThrows(IllegalArgumentException.class, () -> Matrices.requireRectangular(jagged));
    }
}
