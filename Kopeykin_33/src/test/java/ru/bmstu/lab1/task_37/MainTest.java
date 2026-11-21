package ru.bmstu.lab1.task_37;

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
    void shouldReverseOddLengthArray() {
        String actual = runMain("5\n1 2 3 4 5\n");
        Assertions.assertEquals("5 4 3 2 1", actual);
    }

    @Test
    void shouldReverseEvenLengthArray() {
        String actual = runMain("4\n10 20 30 40\n");
        Assertions.assertEquals("40 30 20 10", actual);
    }

    @Test
    void shouldHandleEmptyStream() {
        Assertions.assertEquals("", runMain(""));
    }
}