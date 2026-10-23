package ru.bmstu.lab01.task_49;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/** Проверка задачи 49 */
class Task49Test {

    @Test
    void rows() {
        assertArrayEquals(new long[] {6, 15}, Task49.solve(new int[][] {{1, 2, 3}, {4, 5, 6}}));
    }

    @Test
    void neg() {
        assertArrayEquals(new long[] {-4, 0}, Task49.solve(new int[][] {{-2, 1, -3}, {0, 0, 0}}));
    }

    @Test
    void oneCol() {
        assertArrayEquals(new long[] {-3, 8, 5}, Task49.solve(new int[][] {{-3}, {8}, {5}}));
    }

    @Test
    void oneRow() {
        assertArrayEquals(new long[] {20}, Task49.solve(new int[][] {{4, 6, 10}}));
    }

    @Test
    void bigSum() {
        assertArrayEquals(new long[] {4294967294L, -4294967296L},
                Task49.solve(new int[][] {
                        {Integer.MAX_VALUE, Integer.MAX_VALUE},
                        {Integer.MIN_VALUE, Integer.MIN_VALUE}
                }));
    }

    @Test
    void badMat() {
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(null));
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(new int[0][]));
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(new int[][] {{1, 2}, {3}}));
    }

    @Test
    void console() {
        byte[] data = "2 3\n1 2 3\n4 5 6\n".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buf, true, StandardCharsets.UTF_8)) {
            Task49.run(new ByteArrayInputStream(data), out);
        }
        assertEquals("6 15" + System.lineSeparator(), buf.toString(StandardCharsets.UTF_8));
    }
}
