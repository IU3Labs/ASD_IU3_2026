package ru.bmstu.lab01.task_56;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MatrixMultiplicationTest {

    @Test
    void shouldMultiplySquareMatrices() {
        int[][] left = {{1, 2}, {3, 4}};
        int[][] right = {{5, 6}, {7, 8}};
        // [[1*5+2*7, 1*6+2*8], [3*5+4*7, 3*6+4*8]] = [[19, 22], [43, 50]]
        assertArrayEquals(new int[][] {{19, 22}, {43, 50}},
                MatrixMultiplication.multiply(left, right));
    }

    @Test
    void shouldMultiplyRectangularMatrices() {
        int[][] left = {{1, 2, 3}, {4, 5, 6}};         // 2×3
        int[][] right = {{7, 8}, {9, 10}, {11, 12}};   // 3×2
        // C[0][0] = 1*7 + 2*9 + 3*11 = 58
        // C[0][1] = 1*8 + 2*10 + 3*12 = 64
        // C[1][0] = 4*7 + 5*9 + 6*11 = 139
        // C[1][1] = 4*8 + 5*10 + 6*12 = 154
        assertArrayEquals(new int[][] {{58, 64}, {139, 154}},
                MatrixMultiplication.multiply(left, right));
    }

    @Test
    void shouldHandleIdentityMatrix() {
        int[][] left = {{1, 2}, {3, 4}};
        int[][] identity = {{1, 0}, {0, 1}};
        assertArrayEquals(left, MatrixMultiplication.multiply(left, identity));
    }

    @Test
    void shouldMultiplyByZeroMatrix() {
        int[][] left = {{1, 2}, {3, 4}};
        int[][] zero = {{0, 0}, {0, 0}};
        assertArrayEquals(new int[][] {{0, 0}, {0, 0}},
                MatrixMultiplication.multiply(left, zero));
    }

    @Test
    void shouldMultiplySingleElementMatrices() {
        assertArrayEquals(new int[][] {{42}},
                MatrixMultiplication.multiply(new int[][] {{6}}, new int[][] {{7}}));
    }

    @Test
    void shouldHandleNegativeValues() {
        int[][] left = {{-1, 2}};
        int[][] right = {{3}, {-4}};
        // -1*3 + 2*(-4) = -3 - 8 = -11
        assertArrayEquals(new int[][] {{-11}},
                MatrixMultiplication.multiply(left, right));
    }

    @Test
    void shouldRejectIncompatibleDimensions() {
        int[][] left = {{1, 2, 3}};
        int[][] right = {{1, 2}, {3, 4}};
        assertThrows(IllegalArgumentException.class,
                () -> MatrixMultiplication.multiply(left, right));
    }

    @Test
    void shouldThrowOnIntOverflow() {
        int[][] left = {{Integer.MAX_VALUE, Integer.MAX_VALUE}};
        int[][] right = {{1}, {1}};
        // сумма = MAX_VALUE + MAX_VALUE > int
        assertThrows(ArithmeticException.class,
                () -> MatrixMultiplication.multiply(left, right));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class,
                () -> MatrixMultiplication.multiply(null, new int[][] {{1}}));
    }

    @Test
    void shouldRejectRaggedMatrix() {
        assertThrows(IllegalArgumentException.class,
                () -> MatrixMultiplication.multiply(new int[][] {{1, 2}, {3}}, new int[][] {{1}, {2}}));
    }

    @Test
    void shouldNotModifyInputs() {
        int[][] left = {{1, 2}, {3, 4}};
        int[][] right = {{5, 6}, {7, 8}};
        MatrixMultiplication.multiply(left, right);
        assertArrayEquals(new int[][] {{1, 2}, {3, 4}}, left);
        assertArrayEquals(new int[][] {{5, 6}, {7, 8}}, right);
    }
}