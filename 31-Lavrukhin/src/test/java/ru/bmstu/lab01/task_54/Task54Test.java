package ru.bmstu.lab01.task_54;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.ConsoleHelper;

class Task54Test {

    @Test
    void shouldSumMainDiagonal() {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
        };

        assertEquals(15, Task54.sumOfMainDiagonal(matrix));
    }

    @Test
    void shouldWorkForOneElement() {
        assertEquals(-4, Task54.sumOfMainDiagonal(new int[][] {{-4}}));
    }

    @Test
    void shouldNotOverflowInt() {
        int max = Integer.MAX_VALUE;
        int[][] matrix = {{max, 0}, {0, max}};

        assertEquals(2L * max, Task54.sumOfMainDiagonal(matrix));
    }

    @Test
    void shouldThrowForNotSquareMatrix() {
        assertThrows(IllegalArgumentException.class, () -> Task54.sumOfMainDiagonal(new int[][] {{1, 2, 3}}));
    }

    @Test
    void shouldReadInputAndPrintSum() {
        String output = ConsoleHelper.run(() -> Task54.main(new String[0]), "2\n1 2\n3 4\n");

        assertEquals("5", output);
    }
}
