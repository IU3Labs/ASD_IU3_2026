package ru.bmstu.lab1.task_41;

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
    void shouldRemoveAllOccurrencesOfTarget() {
        String actual = runMain("6\n3 2 2 3 4 3\n3\n");
        Assertions.assertEquals("2 2 4", actual);
    }

    @Test
    void shouldReturnEmptyLineWhenAllElementsRemoved() {
        String actual = runMain("3\n7 7 7\n7\n");
        Assertions.assertEquals("", actual);
    }

    @Test
    void shouldHandleEmptyStream() {
        Assertions.assertEquals("", runMain(""));
    }
}