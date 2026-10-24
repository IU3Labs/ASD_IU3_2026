package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_25Test {

    @Test
    void shouldReturnSumWhenPositiveArrayIsGiven() {
        String input = "4\n5 10 15 20\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_25.main(new String[]{});

        assertEquals("50", out.toString().trim());
    }

    @Test
    void shouldReturnSumWhenNegativeAndPositiveArrayIsGiven() {
        String input = "3\n-5 10 -3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_25.main(new String[]{});

        assertEquals("2", out.toString().trim());
    }
}