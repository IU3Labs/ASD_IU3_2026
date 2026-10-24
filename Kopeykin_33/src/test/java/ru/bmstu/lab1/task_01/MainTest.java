package ru.bmstu.lab1.task_01;

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
    void shouldFindMinAndMaxInGeneralArray() {
        String actual = runMain("6\n3 1 9 -4 7 0\n");
        Assertions.assertEquals("-4 9", actual);
    }

    @Test
    void shouldFindMinAndMaxWhenElementsAreEqual() {
        String actual = runMain("4\n5 5 5 5\n");
        Assertions.assertEquals("5 5", actual);
    }

    @Test
    void shouldHandleEmptyInputGracefully() {
        String actual = runMain("");
        Assertions.assertEquals("", actual);
    }
}