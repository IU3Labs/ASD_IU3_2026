package ru.bmstu.lab1.task_13;

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
    void shouldSortUnsortedArrayAscending() {
        String actual = runMain("5\n64 25 12 22 11\n");
        Assertions.assertEquals("11 12 22 25 64", actual);
    }

    @Test
    void shouldSortArrayWithNegativeAndDuplicateValues() {
        String actual = runMain("4\n0 -5 3 -5\n");
        Assertions.assertEquals("-5 -5 0 3", actual);
    }

    @Test
    void shouldHandleEmptyStream() {
        Assertions.assertEquals("", runMain(""));
    }
}