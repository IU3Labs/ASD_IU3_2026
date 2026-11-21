package ru.bmstu.lab1.task_14;

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
    void shouldSortArrayInNonIncreasingOrder() {
        String actual = runMain("5\n12 11 13 5 6\n");
        Assertions.assertEquals("13 12 11 6 5", actual);
    }

    @Test
    void shouldKeepAlreadyDescendingArray() {
        String actual = runMain("3\n9 5 1\n");
        Assertions.assertEquals("9 5 1", actual);
    }

    @Test
    void shouldHandleEmptyStream() {
        Assertions.assertEquals("", runMain(""));
    }
}