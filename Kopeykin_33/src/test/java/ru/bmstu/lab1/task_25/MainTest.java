package ru.bmstu.lab1.task_25;

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
    void shouldCalculateSumOfElements() {
        String actual = runMain("5\n10 -2 7 0 5\n");
        Assertions.assertEquals("20", actual);
    }

    @Test
    void shouldAvoidIntegerOverflowForLargeValues() {
        String actual = runMain("2\n2000000000 2000000000\n");
        Assertions.assertEquals("4000000000", actual);
    }

    @Test
    void shouldHandleEmptyStream() {
        Assertions.assertEquals("", runMain(""));
    }
}