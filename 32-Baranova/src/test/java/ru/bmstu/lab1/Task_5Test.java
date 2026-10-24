package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_5Test {

    @Test
    void shouldReturnSumOfEvenIndicesWhenArrayIsGiven() {
        String input = "4\n10 20 30 40\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_5.main(new String[]{});

        assertEquals("40", out.toString().trim());
    }

    @Test
    void shouldReturnFirstElementWhenSingleElementArray() {
        String input = "1\n15\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_5.main(new String[]{});

        assertEquals("15", out.toString().trim());
    }
}