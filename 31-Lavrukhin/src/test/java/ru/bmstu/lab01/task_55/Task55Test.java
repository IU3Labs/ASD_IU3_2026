package ru.bmstu.lab01.task_55;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.ConsoleHelper;

class Task55Test {

    @Test
    void shouldSumAntiDiagonal() {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
        };

        // 3 + 5 + 7
        assertEquals(15, Task55.sumOfSecondDiagonal(matrix));
    }

    @Test
    void shouldDifferFromMainDiagonal() {
        int[][] matrix = {{1, 10}, {20, 2}};

        assertEquals(30, Task55.sumOfSecondDiagonal(matrix));
    }

    @Test
    void shouldWorkForOneElement() {
        assertEquals(6, Task55.sumOfSecondDiagonal(new int[][] {{6}}));
    }

    @Test
    void shouldNotOverflowInt() {
        int min = Integer.MIN_VALUE;
        int[][] matrix = {{0, min}, {min, 0}};

        assertEquals(2L * min, Task55.sumOfSecondDiagonal(matrix));
    }

    @Test
    void shouldThrowForNotSquareMatrix() {
        assertThrows(IllegalArgumentException.class, () -> Task55.sumOfSecondDiagonal(new int[][] {{1}, {2}}));
    }

    @Test
    void shouldReadInputAndPrintSum() {
        String output = ConsoleHelper.run(() -> Task55.main(new String[0]), "2\n1 10\n20 2\n");

        assertEquals("30", output);
    }
}
