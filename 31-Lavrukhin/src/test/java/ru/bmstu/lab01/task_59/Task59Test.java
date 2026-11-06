package ru.bmstu.lab01.task_59;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.ConsoleHelper;

class Task59Test {

    @Test
    void shouldCalculateDeterminant() {
        int[][] matrix = {
            {2, -3, 1},
            {2, 0, -1},
            {1, 4, 5},
        };

        assertEquals(BigInteger.valueOf(49), Task59.determinant(matrix));
    }

    @Test
    void shouldReturnOneForIdentityMatrix() {
        int[][] identity = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};

        assertEquals(BigInteger.ONE, Task59.determinant(identity));
    }

    @Test
    void shouldReturnZeroWhenRowsAreProportional() {
        // вторая строка = первая * 2
        int[][] matrix = {{1, 2, 3}, {2, 4, 6}, {7, 8, 9}};

        assertEquals(BigInteger.ZERO, Task59.determinant(matrix));
    }

    @Test
    void shouldChangeSignWhenRowsAreSwapped() {
        int[][] matrix = {{2, -3, 1}, {1, 4, 5}, {2, 0, -1}};

        assertEquals(BigInteger.valueOf(-49), Task59.determinant(matrix));
    }

    @Test
    void shouldNotOverflowForHugeValues() {
        int max = Integer.MAX_VALUE;
        int[][] matrix = {{max, 0, 0}, {0, max, 0}, {0, 0, max}};
        BigInteger expected = BigInteger.valueOf(max).pow(3);

        assertEquals(expected, Task59.determinant(matrix));
    }

    @Test
    void shouldThrowForWrongSize() {
        assertThrows(IllegalArgumentException.class, () -> Task59.determinant(new int[][] {{1, 2}, {3, 4}}));
    }

    @Test
    void shouldReadNineNumbersAndPrintDeterminant() {
        String output = ConsoleHelper.run(() -> Task59.main(new String[0]), "2 -3 1\n2 0 -1\n1 4 5\n");

        assertEquals("49", output);
    }
}
