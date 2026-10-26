package ru.bmstu.lab01.task_52;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
/** Тесты задачи 52 */
class Task52Test {
    @Test
    void normal() {
        assertArrayEquals(new int[] {1, 2, 3}, Task52.solve(new int[][] {{1,2,3},{4,5,6}}));
    }
    @Test
    void negative() {
        assertArrayEquals(new int[] {-2, 0, -3}, Task52.solve(new int[][] {{-2,1,-3},{0,0,0}}));
    }
    @Test
    void limits() {
        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE},
                Task52.solve(
                new int[][] {{Integer.MAX_VALUE,Integer.MIN_VALUE},{Integer.MIN_VALUE,Integer.MAX_VALUE}}));
    }
    @Test
    void one() {
        assertArrayEquals(new int[] {-7}, Task52.solve(new int[][] {{-7}}));
    }
    @Test void badShape() {
        assertThrows(IllegalArgumentException.class, () -> Task52.solve(null));
        assertThrows(IllegalArgumentException.class, () -> Task52.solve(new int[0][]));
        assertThrows(IllegalArgumentException.class, () -> Task52.solve(new int[][] {{1, 2}, {3}}));
        assertThrows(IllegalArgumentException.class, () -> Task52.solve(new int[][] {{1, 2}, null}));
    }
    @Test
    void console() {
        checkIO("2 3\n1 2 3\n4 5 6\n", "1 2 3\n");
    }
    private void checkIO(String input, String expected) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buf, true, StandardCharsets.UTF_8)) {
            Task52.run(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), out);
        }
        assertEquals(expected.replace("\n", System.lineSeparator()), buf.toString(StandardCharsets.UTF_8));
    }
    @Test
    void invalidInput() {
        var in = new ByteArrayInputStream("0 2\n".getBytes(StandardCharsets.UTF_8));
        var out = new PrintStream(new ByteArrayOutputStream());
        assertThrows(IllegalArgumentException.class, () -> Task52.run(in, out));
    }

    @Test
    void shouldNotChangeInput() {
        int[][] a = {{5, -1}, {3, 4}};
        assertArrayEquals(new int[] {3, -1}, Task52.solve(a));
        assertArrayEquals(new int[] {5, -1}, a[0]);
    }

    @Test
    void shouldRejectEmptyRow() {
        assertThrows(IllegalArgumentException.class, () -> Task52.solve(new int[][] {{}}));
        assertThrows(IllegalArgumentException.class, () -> Task52.solve(new int[][] {null}));
    }

    @Test
    void shouldRejectZeroColumns() {
        var in = new ByteArrayInputStream("1 0\n".getBytes(StandardCharsets.UTF_8));
        assertThrows(IllegalArgumentException.class, () -> Task52.run(in, System.out));
    }
}
