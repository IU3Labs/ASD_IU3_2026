package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_3Test {

    @Test
    void shouldReturnCountWhenElementAppearsMultipleTimes() {
        String input = "5\n10 20 10 40 10\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_3.main(new String[]{});

        assertEquals("3", out.toString().trim());
    }

    @Test
    void shouldReturnZeroWhenElementDoesNotExist() {
        String input = "3\n1 2 3\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_3.main(new String[]{});

        assertEquals("0", out.toString().trim());
    }
}