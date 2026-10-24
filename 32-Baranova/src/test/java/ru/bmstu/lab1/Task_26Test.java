package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_26Test {

    @Test
    void shouldReturnProductWhenArrayIsGiven() {
        String input = "3\n2 3 4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_26.main(new String[]{});

        assertEquals("24", out.toString().trim());
    }

    @Test
    void shouldHandleZeroInArray() {
        String input = "3\n5 0 10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_26.main(new String[]{});

        assertEquals("0", out.toString().trim());
    }
}