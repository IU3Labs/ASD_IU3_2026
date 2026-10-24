package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_2Test {

    @Test
    void shouldReturnIndexOfElementWhenElementExists() {
        String input = "5\n10 20 30 40 50\n30\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_2.main(new String[]{});

        assertEquals("2", out.toString().trim());
    }

    @Test
    void shouldReturnMinusOneWhenElementDoesNotExist() {
        String input = "3\n1 2 3\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_2.main(new String[]{});

        assertEquals("-1", out.toString().trim());
    }
}