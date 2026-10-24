package ru.bmstu.lab1.task_03;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {

    private String runMain(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Main.main(new String[0]);
        return out.toString().trim();
    }

    @Test
    void shouldCountMultipleOccurrences() {
        String actual = runMain("6\n4 2 4 1 4 9\n4\n");
        Assertions.assertEquals("3", actual);
    }

    @Test
    void shouldReturnZeroWhenElementAbsent() {
        String actual = runMain("3\n1 2 3\n10\n");
        Assertions.assertEquals("0", actual);
    }

    @Test
    void shouldHandleEmptyStream() {
        Assertions.assertEquals("", runMain(""));
    }
}