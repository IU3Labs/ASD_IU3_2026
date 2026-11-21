package ru.bmstu.lab1.task_15;

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
        return out.toString().trim().replace("\r\n", "\n");
    }

    @Test
    void shouldSortAndCountSwapsCorrectly() {
        String actual = runMain("4\n4 3 2 1\n");
        Assertions.assertEquals("1 2 3 4\n6", actual);
    }

    @Test
    void shouldExitEarlyWithZeroSwapsWhenAlreadySorted() {
        String actual = runMain("4\n1 2 3 4\n");
        Assertions.assertEquals("1 2 3 4\n0", actual);
    }

    @Test
    void shouldHandleEmptyStream() {
        Assertions.assertEquals("", runMain(""));
    }
}