package ru.bmstu.lab01.task_49;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
/** Тесты задачи 49 */
class Task49Test {
    @Test
    void normal() {
        assertArrayEquals(new long[] {6, 15}, Task49.solve(new int[][] {{1,2,3},{4,5,6}}));
    }
    @Test
    void negative() {
        assertArrayEquals(new long[] {-4, 0}, Task49.solve(new int[][] {{-2,1,-3},{0,0,0}}));
    }
    @Test
    void limits() {
        assertArrayEquals(new long[] {2147483647L * 2, -4294967296L},
                Task49.solve(
                new int[][] {{Integer.MAX_VALUE,Integer.MAX_VALUE},{Integer.MIN_VALUE,Integer.MIN_VALUE}}));
    }
    @Test
    void one() {
        assertArrayEquals(new long[] {-7}, Task49.solve(new int[][] {{-7}}));
    }
    @Test void badShape() {
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(null));
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(new int[0][]));
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(new int[][] {{1, 2}, {3}}));
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(new int[][] {{1, 2}, null}));
    }
    @Test
    void console() {
        checkIO("2 3\n1 2 3\n4 5 6\n", "6 15\n");
    }
    private void checkIO(String input, String expected) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buf, true, StandardCharsets.UTF_8)) {
            Task49.run(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), out);
        }
        assertEquals(expected.replace("\n", System.lineSeparator()), buf.toString(StandardCharsets.UTF_8));
    }
    @Test
    void invalidInput() {
        var in = new ByteArrayInputStream("0 2\n".getBytes(StandardCharsets.UTF_8));
        var out = new PrintStream(new ByteArrayOutputStream());
        assertThrows(IllegalArgumentException.class, () -> Task49.run(in, out));
    }

    @Test
    void shouldHandleOneColumn() {
        assertArrayEquals(new long[] {5, -2, 0}, Task49.solve(new int[][] {{5}, {-2}, {0}}));
    }

    @Test
    void shouldRejectMissingFirstRow() {
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(new int[][] {null}));
        assertThrows(IllegalArgumentException.class, () -> Task49.solve(new int[][] {{}}));
    }

    @Test
    void shouldRejectZeroColumns() {
        checkBadInput("2 0\n");
    }

    private void checkBadInput(String input) {
        var in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        var out = new PrintStream(new ByteArrayOutputStream());
        assertThrows(IllegalArgumentException.class, () -> Task49.run(in, out));
    }
}
