package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_31Test {

    @Test
    void shouldReturnScalarProductWhenArraysAreGiven() {
        String input = "3\n1 2 3\n4 5 6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_31.main(new String[]{});

        assertEquals("32", out.toString().trim());
    }

    @Test
    void shouldReturnZeroWhenOrthogonalArrays() {
        String input = "2\n1 0\n0 1\n"; // 1*0 + 0*1 = 0
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_31.main(new String[]{});

        assertEquals("0", out.toString().trim());
    }
}