package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_1Test {

    @Test
    void shouldReturnMinAndMaxWhenValidArrayIsGiven() {
        String input = "5\n10 2 50 1 8\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_1.main(new String[]{});

        assertEquals("1 50", out.toString().trim());
    }
}