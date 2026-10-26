package ru.bmstu.lab01.task_55;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
/** Тесты задачи 55 */
class Task55Test {
    @Test
    void normal() {
        assertEquals(15, Task55.solve(new int[][] {{1,2,3},{4,5,6},{7,8,9}}));
    }
    @Test
    void one() {
        assertEquals(-8, Task55.solve(new int[][] {{-8}}));
    }
    @Test
    void negative() {
        assertEquals(5, Task55.solve(new int[][] {{-1,2},{3,-5}}));
    }
    @Test
    void large() {
        assertEquals(0, Task55.solve(new int[][] {{Integer.MAX_VALUE,0},{0,Integer.MAX_VALUE}}));
    }
    @Test void badShape() {
        assertThrows(IllegalArgumentException.class, () -> Task55.solve(null));
        assertThrows(IllegalArgumentException.class, () -> Task55.solve(new int[][] {{1, 2}}));
        assertThrows(IllegalArgumentException.class, () -> Task55.solve(new int[][] {{1, 2}, null}));
    }
    @Test
    void console() {
        checkIO("3 3\n1 2 3\n4 5 6\n7 8 9\n", "15\n");
    }
    private void checkIO(String input, String expected) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(buf, true, StandardCharsets.UTF_8)) {
            Task55.run(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), out);
        }
        assertEquals(expected.replace("\n", System.lineSeparator()), buf.toString(StandardCharsets.UTF_8));
    }
    @Test
    void invalidInput() {
        var in = new ByteArrayInputStream("0 2\n".getBytes(StandardCharsets.UTF_8));
        var out = new PrintStream(new ByteArrayOutputStream());
        assertThrows(IllegalArgumentException.class, () -> Task55.run(in, out));
    }

    @Test
    void shouldRejectEmptyShape() {
        assertThrows(IllegalArgumentException.class, () -> Task55.solve(new int[0][]));
        assertThrows(IllegalArgumentException.class, () -> Task55.solve(new int[][] {{}}));
        assertThrows(IllegalArgumentException.class, () -> Task55.solve(new int[][] {{1, 2}, null}));
    }

    @Test
    void shouldRejectZeroColumns() {
        var in = new ByteArrayInputStream("2 0\n".getBytes(StandardCharsets.UTF_8));
        assertThrows(IllegalArgumentException.class, () -> Task55.run(in, System.out));
    }
}
