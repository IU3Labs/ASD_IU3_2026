package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_10Test {

    @Test
    void shouldReturnLastIndexWhenMultipleMultiplesExist() {
        String input = "4\n10 15 20 7\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_10.main(new String[]{});

        assertEquals("2", out.toString().trim());
    }

    @Test
    void shouldReturnMinusOneWhenNoMultipleExists() {
        String input = "3\n1 2 3\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_10.main(new String[]{});

        assertEquals("-1", out.toString().trim());
    }
}