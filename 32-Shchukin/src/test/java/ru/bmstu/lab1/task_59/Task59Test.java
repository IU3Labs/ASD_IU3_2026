package ru.bmstu.lab1.task_59;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет решение задачи №59, включая граничные случаи. */
class Task59Test {
    @Test
    void shouldComputeNonZeroDeterminant() {
        assertEquals(BigInteger.valueOf(-306), Task59.determinant(new int[][]{
                        {6, 1, 1}, {4, -2, 5}, {2, 8, 7}}));
    }

    @Test
    void shouldReturnZeroForDependentRows() {
        assertEquals(BigInteger.ZERO, Task59.determinant(new int[][]{
                        {1, 2, 3}, {2, 4, 6}, {0, 1, 2}}));
    }

    @Test
    void shouldReturnOneForIdentity() {
        assertEquals(BigInteger.ONE, Task59.determinant(new int[][]{
                        {1, 0, 0}, {0, 1, 0}, {0, 0, 1}}));
    }

    @Test
    void shouldComputeLargeExactDeterminant() {
        BigInteger maximum = BigInteger.valueOf(Integer.MAX_VALUE);
                    BigInteger expected = maximum.pow(3);
                    assertEquals(expected, Task59.determinant(new int[][]{
                        {Integer.MAX_VALUE, 0, 0},
                        {0, Integer.MAX_VALUE, 0},
                        {0, 0, Integer.MAX_VALUE}}));
    }
}
