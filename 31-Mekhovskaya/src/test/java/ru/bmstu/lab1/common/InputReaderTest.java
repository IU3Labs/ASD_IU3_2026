package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Тесты чтения массивов и матриц.
 */
class InputReaderTest {

    @Test
    void shouldReadArrayWithLeadingSize() {
        int[] actual = InputReader.readArray(new Scanner("3\n5 -2 7"));

        assertArrayEquals(new int[] {5, -2, 7}, actual);
    }

    @Test
    void shouldReadMatrixRowByRow() {
        int[][] actual = InputReader.readMatrix(new Scanner("2 3\n1 2 3\n4 5 6"));

        assertArrayEquals(new int[][] {{1, 2, 3}, {4, 5, 6}}, actual);
    }

    @Test
    void shouldThrowWhenArraySizeIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> InputReader.readArray(new Scanner("-1")));
    }

    @Test
    void shouldThrowWhenMatrixSizeIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> InputReader.readMatrix(new Scanner("2 -3")));
    }
}
