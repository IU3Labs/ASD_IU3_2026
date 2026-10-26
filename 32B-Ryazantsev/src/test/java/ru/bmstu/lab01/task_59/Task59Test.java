package ru.bmstu.lab01.task_59;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
/** Тесты задачи 59 */
class Task59Test {
    @Test void identity() {
        assertEquals(BigInteger.ONE, Task59.solve(new int[][] {{1,0,0},{0,1,0},{0,0,1}}));
    }
    @Test void negative() {
        assertEquals(BigInteger.valueOf(-2), Task59.solve(new int[][] {{-2,0,0},{0,1,0},{0,0,1}}));
    }
    @Test
    void zero() {
        assertEquals(BigInteger.ZERO, Task59.solve(new int[][] {{1,2,3},{1,2,3},{7,8,9}}));
    }
    @Test void big() {
        int x = Integer.MAX_VALUE;
        BigInteger expected = BigInteger.valueOf(x).pow(3);
        assertEquals(expected, Task59.solve(new int[][] {{x,0,0},{0,x,0},{0,0,x}}));
    }
    @Test void badSize() {
        assertThrows(IllegalArgumentException.class, () -> Task59.solve(null));
        assertThrows(IllegalArgumentException.class, () -> Task59.solve(new int[2][2]));
        assertThrows(IllegalArgumentException.class, () -> Task59.solve(new int[][] {{1,2,3},{1,2,3},null}));
        assertThrows(IllegalArgumentException.class, () -> Task59.solve(new int[][] {{1,2,3},{1,2,3},{1,2}}));
    }
    @Test
    void console() {
        checkIO("3 3\n1 0 0\n0 1 0\n0 0 1\n", "1\n");
    }
    private void checkIO(String input, String expected) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buf, true, StandardCharsets.UTF_8)) {
            Task59.run(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), out);
        }
        assertEquals(expected.replace("\n", System.lineSeparator()), buf.toString(StandardCharsets.UTF_8));
    }
    @Test
    void mixed() {
        assertEquals(BigInteger.valueOf(57),
                Task59.solve(new int[][] {{2,-1,0},{3,4,1},{5,2,6}}));
    }
    @Test
    void invalidInput() {
        var in = new ByteArrayInputStream("2 2\n".getBytes(StandardCharsets.UTF_8));
        var out = new PrintStream(new ByteArrayOutputStream());
        assertThrows(IllegalArgumentException.class, () -> Task59.run(in, out));
    }

    @Test
    void shouldRejectShortFirstRow() {
        assertThrows(IllegalArgumentException.class, () ->
                Task59.solve(new int[][] {{1, 2}, {3, 4, 5}, {6, 7, 8}}));
    }

    @Test
    void shouldHandleRowSwap() {
        assertEquals(BigInteger.valueOf(-1),
                Task59.solve(new int[][] {{0, 1, 0}, {1, 0, 0}, {0, 0, 1}}));
    }
}
