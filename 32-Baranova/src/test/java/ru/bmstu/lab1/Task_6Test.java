package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_6Test {

    @Test
    void shouldReturnCountOfElementsGreaterThanAverage() {
        String input = "4\n1 2 3 10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_6.main(new String[]{});

        assertEquals("1", out.toString().trim());
    }

    @Test
    void shouldReturnZeroWhenNoElementGreaterThanAverage() {
        String input = "3\n5 5 5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_6.main(new String[]{});

        assertEquals("0", out.toString().trim());
    }
}