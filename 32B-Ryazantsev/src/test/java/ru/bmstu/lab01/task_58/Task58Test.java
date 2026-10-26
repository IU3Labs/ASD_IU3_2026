package ru.bmstu.lab01.task_58;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
/** Тесты задачи 58 */
class Task58Test {
    @Test
    void rows() {
        assertArrayEquals(new int[][] {{1, 2, 3}, {6, 5, 4}}, Task58.solve(2, 3));
    }
    @Test
    void oneRow() {
        assertArrayEquals(new int[][] {{1, 2, 3}}, Task58.solve(1, 3));
    }
    @Test
    void oneCol() {
        assertArrayEquals(new int[][] {{1}, {2}, {3}}, Task58.solve(3, 1));
    }
    @Test
    void oddRows() {
        assertArrayEquals(new int[][] {{1, 2}, {4, 3}, {5, 6}}, Task58.solve(3, 2));
    }
    @Test void badSize() {
        assertThrows(IllegalArgumentException.class, () -> Task58.solve(0, 2));
        assertThrows(IllegalArgumentException.class, () -> Task58.solve(2, -1));
        assertThrows(IllegalArgumentException.class, () -> Task58.solve(100000, 100000));
    }
    @Test
    void console() {
        checkIO("2 3\n", "1 2 3\n6 5 4\n");
    }
    private void checkIO(String input, String expected) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buf, true, StandardCharsets.UTF_8)) {
            Task58.run(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), out);
        }
        assertEquals(expected.replace("\n", System.lineSeparator()), buf.toString(StandardCharsets.UTF_8));
    }
    @Test
    void singleCell() {
        assertArrayEquals(new int[][] {{1}}, Task58.solve(1, 1));
    }
    @Test
    void invalidInput() {
        var in = new ByteArrayInputStream("0 2\n".getBytes(StandardCharsets.UTF_8));
        var out = new PrintStream(new ByteArrayOutputStream());
        assertThrows(IllegalArgumentException.class, () -> Task58.run(in, out));
    }

    @Test
    void shouldRejectNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () -> Task58.solve(-1, 2));
    }

    @Test
    void shouldPrintOneCell() {
        checkIO("1 1\n", "1\n");
    }
}
