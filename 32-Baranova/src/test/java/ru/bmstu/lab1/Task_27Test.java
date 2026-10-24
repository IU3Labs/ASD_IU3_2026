package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_27Test {

    @Test
    void shouldReturnSumOfSquaresWhenArrayIsGiven() {
        String input = "3\n1 2 3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_27.main(new String[]{});

        assertEquals("14", out.toString().trim());
    }

    @Test
    void shouldHandleNegativeNumbersInSquares() {
        String input = "2\n-3 4\n"; // (-3)^2 + 4^2 = 9 + 16 = 25
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_27.main(new String[]{});

        assertEquals("25", out.toString().trim());
    }
}