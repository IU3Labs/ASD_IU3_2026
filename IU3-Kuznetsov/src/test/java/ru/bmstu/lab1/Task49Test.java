package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task49Test {
    @Test
    void shouldCalculateRowSums() {
        int[][] matrix = {{1, 2}, {3, 4}};
        int[] expected = {3, 7};
        int[] actual = new int[2];
        for (int i = 0; i < 2; i++) {
            int sum = 0;
            for (int j = 0; j < 2; j++) sum += matrix[i][j];
            actual[i] = sum;
        }
        assertArrayEquals(expected, actual);
    }
}