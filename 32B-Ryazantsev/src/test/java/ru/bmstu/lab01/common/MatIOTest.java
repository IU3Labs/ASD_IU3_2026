package ru.bmstu.lab01.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/** Тесты для ввода, вывода и проверки матрицы */
class MatIOTest {
    private static ByteArrayInputStream inp(String s) {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void readNums() {
        int[][] exp = {{-1, 0}, {12, Integer.MIN_VALUE}};
        assertArrayEquals(exp, MatIO.read(inp("2 2\n -1 0\t+12 -2147483648\n")));
    }

    @Test
    void badSize() {
        assertThrows(IllegalArgumentException.class, () -> MatIO.read(inp("0 3")));
        assertThrows(IllegalArgumentException.class, () -> MatIO.read(inp("2 -3")));
    }

    @Test
    void badNums() {
        assertThrows(NoSuchElementException.class, () -> MatIO.read(inp("1 2 7")));
        assertThrows(InputMismatchException.class, () -> MatIO.read(inp("1 1 nope")));
        assertThrows(InputMismatchException.class, () -> MatIO.read(inp("1 1 2147483648")));
    }

    @Test
    void badMat() {
        assertThrows(IllegalArgumentException.class, () -> Mat.cols(new int[][] {null}));
        assertThrows(IllegalArgumentException.class, () -> Mat.cols(new int[][] {{}}));
        assertThrows(IllegalArgumentException.class, () -> Mat.cols(new int[][] {{1}, null}));
        assertEquals(2, Mat.cols(new int[][] {{2, 3}, {4, 5}}));
    }

    @Test
    void printNums() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buf, true, StandardCharsets.UTF_8)) {
            MatIO.print(new int[] {3, -1, 5}, out);
            MatIO.print(new long[] {4294967294L, -1}, out);
        }
        assertEquals("3 -1 5" + System.lineSeparator() + "4294967294 -1" + System.lineSeparator(),
                buf.toString(StandardCharsets.UTF_8));
    }

    @Test
    void printShort() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buf, true, StandardCharsets.UTF_8)) {
            MatIO.print(new int[] {9}, out);
            MatIO.print(new long[] {8}, out);
            MatIO.print(new int[0], out);
            MatIO.print(new long[0], out);
        }
        assertEquals("9" + System.lineSeparator() + "8" + System.lineSeparator()
                + System.lineSeparator() + System.lineSeparator(), buf.toString(StandardCharsets.UTF_8));
    }
}
