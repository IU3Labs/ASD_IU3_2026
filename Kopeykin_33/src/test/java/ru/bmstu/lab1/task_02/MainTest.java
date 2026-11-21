package ru.bmstu.lab1.task_02;

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
    void shouldReturnFirstIndexWhenDuplicatesExist() {
        String actual = runMain("5\n10 20 30 20 50\n20\n");
        Assertions.assertEquals("1", actual);
    }

    @Test
    void shouldReturnMinusOneWhenElementIsAbsent() {
        String actual = runMain("4\n1 2 3 4\n99\n");
        Assertions.assertEquals("-1", actual);
    }

    @Test
    void shouldHandleEmptyStream() {
        Assertions.assertEquals("", runMain(""));
    }
}