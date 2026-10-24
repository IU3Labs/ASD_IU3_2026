package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_7Test {

    @Test
    void shouldReturnSecondMaxWhenArrayIsGiven() {
        String input = "4\n10 5 20 15\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_7.main(new String[]{});

        assertEquals("15", out.toString().trim());
    }

    @Test
    void shouldReturnSecondMaxWithNegativeNumbers() {
        String input = "3\n-10 -5 -20\n"; // Максимум -5, второй максимум -10
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_7.main(new String[]{});

        assertEquals("-10", out.toString().trim());
    }
}